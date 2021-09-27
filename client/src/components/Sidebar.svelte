<script>
    export let newUserId;
    export let chatContent;
    export let currentChatId;

    export let showSidebar;

    export let addNewUser;

    const isEmpty = (obj) => {
        for (let _ in obj) return false;

        return true;
    };
</script>

<div class={showSidebar ? "sidebar" : "disable"}>
    <div class="sidebar-header">Participants</div>

    {#if !isEmpty(chatContent) && chatContent[currentChatId] && Object.prototype.hasOwnProperty.call(chatContent[currentChatId], "chatUserDTOs")}
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
        <button on:click={addNewUser} class="sidebar-input-submit">Add</button>
    </div>
</div>

<style>
    .flex {
        display: flex;
    }

    .sidebar {
        width: 300px;
        border-left: 1px solid #ddd;
        box-sizing: border-box;
        padding: 15px;
    }

    .sidebar-header {
        font-size: 20px;
        font-weight: bold;
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

    .disable {
        display: none;
    }
</style>
