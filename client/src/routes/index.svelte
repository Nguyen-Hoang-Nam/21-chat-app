<script>
    import { onMount } from "svelte";
    import { goto } from "@sapper/app.mjs";
    import { authenticated, token } from "../stores/auth";
    import { Client } from "@stomp/stompjs";

    import ListChatrooms from "../components/ListChatrooms.svelte";
    import Chatroom from "../components/Chatroom.svelte";
    import CreateChatroomModal from "../components/CreateChatroomModal.svelte";

    let jwtToken;
    let authenticate = false;

    let userId;
    let username;

    let content;
    let chatContent = {};

    let newUserId = "";

    let currentChatName = "";
    let currentChatId = "";
    let listChatroomOrder = [];

    let currentMessage = "";

    let chatName = "";
    let showModal = false;
    let clientAvaiable = false;

    let client;

    token.subscribe((value) => (jwtToken = value));
    authenticated.subscribe((value) => (authenticate = value));

    const addNewMessage = async (message, chatId) => {
        const body = JSON.parse(message.body);

        if (chatContent[chatId]) {
            if (chatContent[chatId]["messageDTOs"]) {
                chatContent[chatId]["messageDTOs"].push(body);
            } else {
                chatContent[chatId]["messageDTOs"] = [body];
            }
        }

        if (listChatroomOrder[0]["id"] !== chatId) {
            let currentChatByChatId = {};
            let chatIdPosition = -1;
            for (let i = 0; i < listChatroomOrder.length; i++) {
                if (listChatroomOrder[i]["id"] === chatId) {
                    currentChatByChatId = listChatroomOrder[i];
                    chatIdPosition = i;
                    break;
                }
            }

            if (chatIdPosition !== -1) {
                listChatroomOrder.splice(chatIdPosition, 1);
                listChatroomOrder.unshift(currentChatByChatId);
            }
        }

        chatContent = chatContent;
        listChatroomOrder = listChatroomOrder;
    };

    const sendMessage = (chatId) => {
        const instantMessage = {
            userId,
            username,
            content: currentMessage,
        };

        currentMessage = "";

        client.publish({
            destination: "/chat/message/" + chatId,
            body: JSON.stringify(instantMessage),
        });
    };

    const fetchChatContent = async (chatId) => {
        if (!Object.prototype.hasOwnProperty.call(chatContent, chatId)) {
            const response = await fetch(
                "http://localhost:8080/chat/get-chat/" + chatId,
                {
                    mode: "cors",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: "Bearer " + jwtToken,
                    },
                }
            );

            response
                .json()
                .then((data) => {
                    console.log(data);
                    chatContent[chatId] = data;
                })
                .catch(() => (chatContent[chatId] = []));
        }
    };

    const fetchContent = async () => {
        if (authenticate) {
            try {
                const response = await fetch("http://localhost:8080/user", {
                    mode: "cors",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: "Bearer " + jwtToken,
                    },
                });

                response.json().then(async (data) => {
                    content = data;

                    username = data["username"];
                    userId = data["id"];
                    if (data["userChatDTOs"]) {
                        listChatroomOrder = [...data["userChatDTOs"]];
                        const currentChat = data["userChatDTOs"][0];

                        currentChatName = currentChat.chatName;
                        currentChatId = currentChat.id;

                        await fetchChatContent(currentChatId);

                        client = new Client({
                            brokerURL: "ws://localhost:8080/ws",
                            debug: (str) => {
                                console.log(str);
                            },
                            reconnectDelay: 5000,
                            heartbeatIncoming: 4000,
                            heartbeatOutgoing: 4000,
                        });

                        client.onConnect = function () {
                            clientAvaiable = true;
                        };

                        client.activate();
                    }

                    console.log(content);
                });
            } catch (e) {
                authenticated.set(false);
                goto("/login");
            }
        } else {
            goto("/login");
        }
    };

    const createChatroom = () => {
        fetch("http://localhost:8080/chat/create", {
            method: "POST",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + jwtToken,
            },
            body: JSON.stringify({
                chatName,
            }),
        })
            .then((res) =>
                res.text().then(async (data) => {
                    console.log(data);
                    const userChatDTO = {
                        id: data,
                        chatName,
                    };

                    listChatroomOrder.push(userChatDTO);
                    content["userChatDTOs"].push(userChatDTO);

                    const chatDTO = {
                        id: data,
                        chatName,
                        messageDTOs: null,
                        chatUserDTOs: [
                            {
                                id: userId,
                                username,
                            },
                        ],
                    };

                    chatContent[data] = chatDTO;

                    chatName = "";
                    showModal = false;
                    content = content;
                })
            )
            .catch(async () => {
                goto("/login");
            });
    };

    const addNewUser = async () => {
        const response = await fetch(
            "http://localhost:8080/chat/add-user/" + currentChatId,
            {
                method: "POST",
                mode: "cors",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: "Bearer " + jwtToken,
                },
                body: JSON.stringify({
                    userId: newUserId,
                }),
            }
        );

        response.text().then((value) => {
            console.log(value);
            chatContent[currentChatId]["chatUserDTOs"].push({
                id: currentChatId,
                username: value,
            });

            newUserId = "";

            chatContent = chatContent;
        });
    };

    onMount(fetchContent);

    $: if (clientAvaiable) {
        console.log(content["userChatDTOs"]);
        for (let i = 0; i < content["userChatDTOs"].length; i++) {
            client.subscribe(
                "/topic/message." + content["userChatDTOs"][i]["id"],
                (message) =>
                    addNewMessage(message, content["userChatDTOs"][i]["id"])
            );
        }

        clientAvaiable = false;
    }
</script>

<svelte:head>
    <title>Spring Svelte chat app</title>
</svelte:head>

<CreateChatroomModal bind:showModal bind:chatName {createChatroom} />

<div class="container flex">
    {#if content}
        <ListChatrooms
            bind:showModal
            bind:content
            bind:currentChatId
            bind:currentChatName
            {listChatroomOrder}
            {fetchChatContent}
        />

        <Chatroom
            bind:currentMessage
            bind:currentChatId
            bind:currentChatName
            bind:chatContent
            bind:userId
            bind:newUserId
            {sendMessage}
            {addNewUser}
        />
    {/if}
</div>

<style>
    .flex {
        display: flex;
    }

    .container {
        width: 100%;
        height: 100%;
    }
</style>
