<script>
    import { afterUpdate } from "svelte";
    export let chatContent;
    export let currentChatId;

    export let userId;

    let chatMessage;

    const checkFileType = (filename) => {
        const filenameComponents = filename.split(".");
        const fileType = filenameComponents[filenameComponents.length - 1];

        return fileType;
    };

    const isDisplay = (filename) => {
        const imageType = ["jpg", "jpeg", "png"];
        const fileType = checkFileType(filename);

        if (imageType.includes(fileType)) {
            return true;
        }

        return false;
    };

    const dateToHours = (timeStr) => {
        const time = new Date(timeStr);
        let result = "";

        let hours = time.getHours();
        let day = "AM";
        if (hours > 12) {
            hours = hours - 12;
            day = "PM";
        }

        result = hours + ":" + time.getSeconds() + " " + day;
        return result;
    };

    const isEmpty = (obj) => {
        for (let _ in obj) return false;

        return true;
    };

    afterUpdate(() => {
        chatMessage.scrollTop = chatMessage.scrollHeight;
    });
</script>

<div class="chatroom-content" bind:this={chatMessage}>
    {#if !isEmpty(chatContent) && chatContent[currentChatId] && chatContent[currentChatId]["messageDTOs"]}
        {#each chatContent[currentChatId]["messageDTOs"] as chat, i}
            <div
                class={chat.userId === userId
                    ? "flex h-right v-center my-message"
                    : "flex v-center"}
            >
                {#if i === 0 || chat.userId !== chatContent[currentChatId]["messageDTOs"][i - 1].userId}
                    <div class="message-username">
                        {chat.username}, {dateToHours(chat.time)}
                    </div>
                {/if}
            </div>

            <div
                class={chat.userId === userId
                    ? "flex h-right v-center my-message border-top-left-radius border-bottom-left-radius"
                    : "flex v-center border-top-right-radius border-bottom-right-radius"}
            >
                {#if chat.type === "text"}
                    <div class="message message-text">
                        {chat.content}
                    </div>
                {:else if chat.type === "file"}
                    {#if isDisplay(chat.content)}
                        <img
                            class="message message-image"
                            src={"http://localhost:8080/chat/files/" +
                                chat.content}
                            alt="Show"
                        />
                    {:else}
                        <div class="message">
                            {chat.content}
                        </div>
                    {/if}
                {/if}
            </div>
        {/each}
    {/if}
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

    .chatroom-content {
        flex-grow: 1;
        padding: 0 20px 20px 20px;
        overflow-y: auto;
        overflow-x: hidden;
    }

    .message {
        margin-bottom: 1px;
    }

    .message-text {
        padding: 10px 20px;
        font-size: 15px;
        background: #eee;
        max-width: 60%;
    }

    .message-image {
        max-width: 30%;
        max-height: 30%;
    }

    .border-top-left-radius .message {
        border-top-left-radius: 15px;
    }

    .border-top-right-radius .message {
        border-top-right-radius: 15px;
    }

    .border-bottom-right-radius .message {
        border-bottom-right-radius: 15px;
    }

    .border-bottom-left-radius .message {
        border-bottom-left-radius: 15px;
    }

    .message-username {
        padding-top: 20px;
        font-size: 14px;
        color: #888;
    }

    .my-message .message {
        background: #0084ff;
        color: white;
        border: none;
    }
</style>
