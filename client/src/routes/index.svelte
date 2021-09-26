<script>
    import { onMount } from "svelte";
    import { goto } from "@sapper/app.mjs";
    import { authenticated, token } from "../stores/auth";
    import { Client } from "@stomp/stompjs";

    let jwtToken;
    let content;
    let chatContent = {};
    let authenticate = false;

    let username;
    let userId;

    let newUserId;

    let currentChatName = "";
    let currentChatId = "";

    let currentMessage = "";

    let chatName;
    let showModal = false;

    let clientAvaiable = false;

    token.subscribe((value) => (jwtToken = value));
    authenticated.subscribe((value) => (authenticate = value));

    let client;

    function addNewMessage(message, chatId) {
        const body = JSON.parse(message.body);

        if (body.userId !== userId) {
            if (chatContent[chatId]["messageDTOs"]) {
                chatContent[chatId]["messageDTOs"].push(body);
            } else {
                chatContent[chatId]["messageDTOs"] = [body];
            }
        }

        chatContent = chatContent;
    }

    function sendMessage(chatId) {
        const instantMessage = {
            userId,
            username,
            content: currentMessage,
        };

        currentMessage = "";

        if (chatContent[chatId]["messageDTOs"]) {
            chatContent[chatId]["messageDTOs"].push(instantMessage);
        } else {
            chatContent[chatId]["messageDTOs"] = [instantMessage];
        }

        chatContent = chatContent;

        console.log(chatContent);

        client.publish({
            destination: "/chat/message/" + chatId,
            body: JSON.stringify(instantMessage),
        });
    }

    const findCurrentChat = (contents) => {
        for (let i = 0; i < contents["userChatDTOs"].length; i++) {
            if (contents["userChatDTOs"][i].id === currentChatId) {
                return contents["userChatDTOs"][i];
            }
        }
    };

    const isEmpty = (obj) => {
        for (let _ in obj) return false;

        return true;
    };

    const fetchChatContent = async () => {
        if (!Object.prototype.hasOwnProperty.call(chatContent, currentChatId)) {
            const response = await fetch(
                "http://localhost:8080/chat/get-chat/" + currentChatId,
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
                    chatContent[currentChatId] = data;
                })
                .catch(() => (chatContent[currentChatId] = []));
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
                        const currentChat = data["userChatDTOs"][0];

                        currentChatName = currentChat.chatName;
                        currentChatId = currentChat.id;

                        await fetchChatContent();

                        client = new Client({
                            brokerURL: "ws://localhost:8080/ws",
                            debug: (str) => {
                                console.log(str);
                            },
                            reconnectDelay: 5000,
                            heartbeatIncoming: 4000,
                            heartbeatOutgoing: 4000,
                        });

                        client.onConnect = function (frame) {
                            console.log("Connected: " + frame);

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
    }

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
            .then(async () => {
                await fetchContent();
                chatName = "";
                showModal = false;
            })
            .catch(async () => {
                goto("/login");
            });
    };

    const newChatModal = () => {
        showModal = true;
    };

    const exitModal = () => {
        showModal = false;
    };

    const changeChatroom = async (chatId) => {
        currentChatId = chatId;

        await fetchChatContent();
        currentChatName = findCurrentChat(content).chatName;
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
</script>

<svelte:head>
    <title>Spring Svelte chat app</title>
</svelte:head>

<div
    class={!showModal
        ? "modal flex v-center h-center disable"
        : "modal flex v-center h-center"}
>
    <form on:submit|preventDefault={createChatroom} class="modal-form flex">
        <h1 class="text-center modal-h1">New Chatroom</h1>

        <label for="chatname" class="modal-label">Chatroom name</label>

        <input
            bind:value={chatName}
            class="modal-input"
            type="text"
            id="chatname"
        />
        <button class="modal-button" type="submit">Submit</button>
    </form>

    <button class="modal-exit" on:click={exitModal}>x</button>
</div>

<div class="container flex">
    {#if content}
        <div class="list-chatroom">
            <div class="list-chatroom-option flex v-center">
                <button class="list-chatroom-button" on:click={newChatModal}
                    >New chat</button
                >
            </div>

            {#if content["userChatDTOs"] !== null}
                {#each content["userChatDTOs"] as chat}
                    <div
                        class={chat.id === currentChatId
                            ? "chatroom-item-choose chatroom-item"
                            : "chatroom-item"}
                        on:click={() => changeChatroom(chat.id)}
                    >
                        <p class="chatroom-item-name">{chat.chatName}</p>
                        <p class="chatroom-item-message">
                            {chat.lastUsername ? chat.lastUsername + ":" : ""}
                            {chat.lastMessage ? chat.lastMessage : ""}
                        </p>
                    </div>
                {/each}
            {/if}
        </div>

        <div class="chatroom flex">
            <div class="chatroom-header">{currentChatName}</div>

            <div class="chatroom-content">
                {#if !isEmpty(chatContent) && chatContent[currentChatId]["messageDTOs"]}
                    {#each chatContent[currentChatId]["messageDTOs"] as chat}
                        <div
                            class={chat.userId === userId
                                ? "flex h-right v-center"
                                : "flex"}
                        >
                            {#if chat.userId !== userId}
                                <div class="message-username">
                                    {chat.username}:
                                </div>
                            {/if}
                            <div class="message">
                                {chat.content}
                            </div>
                        </div>
                    {/each}
                {/if}
            </div>

            <div class="chatroom-input flex">
                <input
                    bind:value={currentMessage}
                    class="chatroom-input-typing"
                    type="text"
                />
                <button
                    class="chatroom-input-send"
                    on:click={() => sendMessage(currentChatId)}>Send</button
                >
            </div>
        </div>

        <div class="sidebar">
            <div class="sidebar-header">Participants</div>

            {#if !isEmpty(chatContent) && Object.prototype.hasOwnProperty.call(chatContent[currentChatId], "chatUserDTOs")}
                {#each chatContent[currentChatId]["chatUserDTOs"] as user}
                    <div>{user.username}</div>
                {/each}
            {/if}

            <div class="sidebar-input flex">
                <input
                    class="sidebar-input-typing"
                    bind:value={newUserId}
                    type="text"
                />
                <button on:click={addNewUser} class="sidebar-input-submit"
                    >Add</button
                >
            </div>
        </div>
    {/if}
</div>

<style>
    .flex {
        display: flex;
    }

    .v-center {
        align-items: center;
    }

    .h-center {
        justify-content: center;
    }

    .h-right {
        justify-content: flex-end;
    }

    .modal {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(50, 50, 50, 0.5);
    }

    .modal-exit {
        position: absolute;
        top: 70px;
        right: 15px;
        color: red;
        font-weight: bold;
        font-size: 20px;
    }

    .container {
        width: 100%;
        height: 100%;
    }

    .list-chatroom {
        width: 300px;
        border-right: 1px solid #ddd;
    }

    .list-chatroom-option {
        height: 30px;
        border-bottom: 1px solid #ddd;
        padding-left: 15px;
        padding-right: 15px;
    }

    .list-chatroom-button {
        height: 20px;
    }

    .chatroom-item {
        width: 100%;
        box-sizing: border-box;
        padding: 10px 15px 10px 15px;
        border-bottom: 1px solid #ddd;
        height: 100px;
    }

    .chatroom-item-choose {
        background: #eee;
    }

    p {
        margin: 0;
    }

    .chatroom-item-name {
        font-size: 20px;
        font-weight: bold;
        padding-bottom: 10px;
    }

    .chatroom-item-message {
        font-size: 12px;
    }

    .chatroom {
        flex-grow: 1;
        flex-direction: column;
    }

    .chatroom-header {
        height: 50px;
        padding: 10px 15px 10px 15px;
        box-sizing: border-box;
        font-size: 20px;
        border-bottom: 2px solid #ddd;
    }

    .chatroom-content {
        flex-grow: 1;
        padding: 20px;
    }

    .chatroom-input {
        padding-left: 15px;
        padding-right: 15px;
        box-sizing: border-box;
        padding-bottom: 10px;
    }

    .chatroom-input-typing {
        height: 25px;
        font-size: 15px;
        padding-left: 20px;
        border: 1px solid #ddd;
        border-radius: 25px;
        flex-grow: 1;
    }

    .chatroom-input-send {
        height: 25px;
        margin-left: 20px;
        border: 1px solid #ddd;
        cursor: pointer;
    }

    .modal-form {
        padding: 20px;
        flex-direction: column;
        border: 1px solid #ddd;
        border-radius: 5px;
        width: 250px;
        background: white;
    }

    .modal-h1 {
        font-size: 30px;
    }

    .modal-label {
        margin: 5px;
        font-size: 18px;
    }

    .modal-input {
        font-size: 20px;
        height: 30px;
        margin-bottom: 20px;
        border: 1px solid #ddd;
        border-radius: 5px;
    }

    .modal-button {
        height: 30px;
        font-size: 20px;
        margin-top: 20px;
        cursor: pointer;
        border: 1px solid #888;
        border-radius: 5px;
    }

    .text-center {
        text-align: center;
    }

    .disable {
        display: none;
    }

    .message {
        height: 25px;
        font-size: 15px;
        padding-left: 20px;
        padding-right: 20px;
        border: 1px solid #ddd;
        border-radius: 25px;
        background: #eee;
        margin-bottom: 10px;
    }

    .message-username {
        font-size: 15px;
        padding-right: 10px;
    }

    .sidebar {
        width: 200px;
        border-left: 1px solid #ddd;

        padding: 15px;
    }

    .sidebar-header {
        font-size: 20px;
    }

    .sidebar-input {
        padding-top: 20px;
    }

    .sidebar-input-typing {
        height: 20px;
        font-size: 15px;
        border: none;
        border-bottom: 1px solid #ddd;
        flex-shrink: 1;
        min-width: 0;
    }

    .sidebar-input-submit {
        height: 20px;
        margin-left: 20px;
    }
</style>
