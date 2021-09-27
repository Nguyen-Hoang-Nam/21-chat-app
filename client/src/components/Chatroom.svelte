<script>
    import Sidebar from "./Sidebar.svelte";

    export let currentMessage;
    export let currentChatName;

    export let chatContent;
    export let currentChatId;

    export let userId;
    export let newUserId;

    export let sendMessage;
    export let addNewUser;

    let showSidebar = true;

    const isEmpty = (obj) => {
        for (let _ in obj) return false;

        return true;
    };

    const toggleSidebar = () => {
        showSidebar = !showSidebar;
    };
</script>

<div class={!isEmpty(chatContent) ? "chatroom" : "disable"}>
    <div class="chatroom-header">
        <div class="chatroom-title">{currentChatName}</div>
        <div class="chatroom-header-options flex v-center">
            <button
                class="chatroom-sidebar-toggle flex v-center h-center"
                on:click={toggleSidebar}
            >
                <img
                    src="./images/info.svg"
                    alt="Show info"
                    class="chatroom-sidebar-toggle-icon"
                />
            </button>
        </div>
    </div>

    <div class="chatroom-container flex">
        <div class="chatroom-main flex">
            <div class="chatroom-content">
                {#if !isEmpty(chatContent) && chatContent[currentChatId] && chatContent[currentChatId]["messageDTOs"]}
                    {#each chatContent[currentChatId]["messageDTOs"] as chat}
                        <div
                            class={chat.userId === userId
                                ? "flex h-right v-center my-message"
                                : "flex v-center"}
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
                    class="chatroom-input-send flex v-center h-center"
                    on:click={() => sendMessage(currentChatId)}
                >
                    <img
                        src="./images/send.svg"
                        class="chatroom-input-send-icon"
                        alt="Send message"
                    />
                </button>
            </div>
        </div>

        <Sidebar
            bind:newUserId
            bind:chatContent
            bind:currentChatId
            {showSidebar}
            {addNewUser}
        />
    </div>
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

    .chatroom {
        flex-grow: 1;
    }

    .chatroom-container {
        height: calc(100% - 50px);
    }

    .chatroom-main {
        flex-grow: 1;
        flex-direction: column;
    }

    .chatroom-header {
        height: 50px;
        padding: 10px 15px 10px 15px;
        box-sizing: border-box;
        font-size: 20px;
        border-bottom: 1px solid #ddd;
        position: relative;
    }

    .chatroom-title {
        width: 100%;
        text-align: center;
        font-weight: bold;
    }

    .chatroom-header-options {
        height: 50px;
        position: absolute;
        right: 15px;
        top: 0px;
    }

    .chatroom-sidebar-toggle {
        width: 30px;
        height: 30px;
        border: 1px solid #ddd;
        border-radius: 100%;

        cursor: pointer;
    }

    .chatroom-sidebar-toggle-icon {
        height: 20px;
    }

    .chatroom-content {
        flex-grow: 1;
        padding: 20px;
        overflow-y: auto;
        overflow-x: hidden;
    }

    .chatroom-input {
        padding-left: 15px;
        padding-right: 15px;
        box-sizing: border-box;
        padding-top: 10px;
        padding-bottom: 10px;
        border-top: 1px solid #ddd;
    }

    .chatroom-input-typing {
        height: 25px;
        font-size: 15px;
        padding-left: 20px;
        border: 1px solid #ddd;
        border-radius: 25px;
        flex-grow: 1;
        outline: none;
    }

    .chatroom-input-send {
        height: 29px;
        width: 29px;
        margin-left: 20px;
        border: 1px solid #ddd;
        cursor: pointer;
        border-radius: 100%;
    }

    .chatroom-input-send-icon {
        width: 20px;
    }

    .message {
        padding: 10px 20px;
        font-size: 15px;
        border: 1px solid #ddd;
        border-radius: 25px;
        background: #eee;
        margin-bottom: 10px;
        max-width: 60%;
    }

    .message-username {
        font-size: 15px;
        padding-right: 10px;
        margin-bottom: 10px;
    }

    .my-message .message {
        background: #0084ff;
        color: white;
        border: none;
    }

    .disable {
        display: none;
    }
</style>
