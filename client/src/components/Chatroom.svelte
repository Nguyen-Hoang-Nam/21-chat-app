<script>
    export let currentMessage;
    export let currentChatName;

    export let chatContent;
    export let currentChatId;

    export let userId;

    export let sendMessage;

    const isEmpty = (obj) => {
        for (let _ in obj) return false;

        return true;
    };
</script>

<div class="chatroom flex">
    <div class="chatroom-header">{currentChatName}</div>

    <div class="chatroom-content">
        {#if !isEmpty(chatContent) && chatContent[currentChatId] && chatContent[currentChatId]["messageDTOs"]}
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

<style>
    .flex {
        display: flex;
    }

    .h-right {
        justify-content: flex-end;
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
</style>
