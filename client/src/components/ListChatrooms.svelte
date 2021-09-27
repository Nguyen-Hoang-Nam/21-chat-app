<script>
    export let showModal;
    export let content;

    export let currentChatId;
    export let currentChatName;

    export let listChatroomOrder;

    export let fetchChatContent;

    const newChatModal = () => {
        showModal = true;
    };

    const findCurrentChat = (contents, chatId) => {
        for (let i = 0; i < contents["userChatDTOs"].length; i++) {
            if (contents["userChatDTOs"][i].id === chatId) {
                return contents["userChatDTOs"][i];
            }
        }
    };

    const changeChatroom = async (chatId) => {
        currentChatId = chatId;

        await fetchChatContent(chatId);
        currentChatName = findCurrentChat(content, chatId).chatName;
    };
</script>

<div class="list-chatroom">
    <div class="list-chatroom-option flex v-center h-right">
        <button class="list-chatroom-button" on:click={newChatModal}>
            <img
                src="./images/add.svg"
                alt="Add chatroom"
                class="list-chatroom-add-icon"
            />
        </button>
    </div>

    <div class="list-chatroom-container">
        {#if listChatroomOrder !== null}
            {#each listChatroomOrder as chat}
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
</div>

<style>
    .flex {
        display: flex;
    }

    .v-center {
        align-items: center;
    }

    .h-right {
        justify-content: flex-end;
    }

    .list-chatroom {
        width: 300px;
        border-right: 1px solid #ddd;
    }

    .list-chatroom-option {
        height: 50px;
        border-bottom: 1px solid #ddd;
        padding-left: 15px;
        padding-right: 15px;
    }

    .list-chatroom-container {
        height: calc(100% - 51px);
        overflow-y: auto;
        overflow-x: hidden;
    }

    .list-chatroom-button {
        padding: 2px;
        cursor: pointer;
        border: 1px solid #ddd;
    }

    .list-chatroom-add-icon {
        height: 20px;
    }

    .chatroom-item {
        width: 100%;
        box-sizing: border-box;
        padding: 10px 15px 10px 15px;
        border-bottom: 1px solid #ddd;
        height: 100px;
        cursor: pointer;
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
</style>
