<script>
    export let showModal;
    export let chatName;

    export let createChatroom;

    const exitModal = () => {
        showModal = false;
    };
</script>

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

    <button class="modal-exit" on:click={exitModal}>
        <img src="./images/exit.svg" class="modal-exit-icon" alt="Exit" />
    </button>
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

    .modal {
        position: absolute;
        z-index: 10;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(50, 50, 50, 0.5);
    }

    .modal-exit {
        position: absolute;
        top: 20px;
        right: 15px;
        color: red;
        font-weight: bold;
        border: none;
        background: transparent;
        cursor: pointer;
    }

    .modal-exit-icon {
        width: 20px;
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
</style>
