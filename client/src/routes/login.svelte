<script>
    import { goto } from "@sapper/app.mjs";
    import { authenticated, token } from "../stores/auth";

    let username = "";
    let password = "";

    let userAuthenticated = false;

    const submit = async () => {
        await fetch("http://localhost:8080/user/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                username,
                password,
            }),
        }).then((res) => {
            const jwtToken = res.headers.get("token");
            token.set(jwtToken);
            authenticated.set(true);

            if (jwtToken !== "") {
                goto("/");
            }
        });
    };

    authenticated.subscribe((auth) => {
        userAuthenticated = auth;
    });

    if (userAuthenticated) {
        goto("/");
    }
</script>

<svelte:head>
    <title>Login</title>
</svelte:head>

<div class="container flex v-center h-center">
    <form on:submit|preventDefault={submit} class="form flex">
        <h1 class="text-center">Login</h1>
        <label for="username">Username</label>
        <input bind:value={username} type="text" id="username" />
        <label for="password">Password</label>
        <input bind:value={password} type="text" id="password" />
        <button type="submit">Login</button>
    </form>
</div>

<style>
    .container {
        height: 100%;
    }

    .flex {
        display: flex;
    }

    .h-center {
        justify-content: center;
    }

    .v-center {
        align-items: center;
    }

    .form {
        padding: 20px;
        flex-direction: column;
        border: 1px solid #ddd;
        border-radius: 5px;
        width: 250px;
    }

    h1 {
        font-size: 30px;
    }

    label {
        margin: 5px;
        font-size: 18px;
    }

    input {
        font-size: 20px;
        height: 30px;
        margin-bottom: 20px;
        border: 1px solid #ddd;
        border-radius: 5px;
    }

    button {
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
</style>
