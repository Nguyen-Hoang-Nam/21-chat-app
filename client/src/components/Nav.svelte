<script>
    import { authenticated } from "../stores/auth";
    import { goto } from "@sapper/app.mjs";

    let auth = false;

    authenticated.subscribe((a) => (auth = a));

    const logout = async () => {
        await fetch("http://localhost:8080/user/logout", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
        });

        goto("/login");
    };
</script>

<div class="nav flex h-space-between v-center">
    <div class="nav-header">
        <a class="nav-link" href="/">Spring Svelte Chat App</a>
    </div>
    <div class="nav-option flex v-center">
        {#if auth}
            <div>
                <p class="nav-link" on:click={logout}>Sign out</p>
            </div>
        {:else}
            <div><a class="nav-link" href="/signup">Sign Up</a></div>
            <div><a class="nav-link" href="/login">Login</a></div>
        {/if}
    </div>
</div>

<style>
    .nav {
        width: 100%;
        height: 50px;
        border-bottom: 1px solid #ddd;
        box-sizing: border-box;
        padding-left: 15px;
        padding-right: 15px;
    }

    .nav-header {
        font-size: 20px;
    }

    .nav-option {
        column-gap: 20px;
    }

    .nav-link {
        text-decoration: none;
    }

    .flex {
        display: flex;
    }

    .h-space-between {
        justify-content: space-between;
    }

    .v-center {
        align-items: center;
    }
</style>
