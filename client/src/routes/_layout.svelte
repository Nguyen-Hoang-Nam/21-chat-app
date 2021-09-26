<script>
    import Nav from "../components/Nav.svelte";
</script>

<Nav />

<main class="main">
    <slot />
</main>

<style>
    .main {
        height: calc(100% - 50px);
    }
</style>
