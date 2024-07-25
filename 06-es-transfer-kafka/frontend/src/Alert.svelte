<script lang="ts">
    // imports libraries
    import { onMount } from 'svelte'
    // variables
    let visible: boolean = false;
    // lifecycle
    onMount(() => {
    })
    // commands
    export function show() {
        visible = true;
    }

    function hide() {
        visible = false;
    }
</script>

{#if visible}
    <div class="alert alert-primary alert-dismissible fade show" role="alert">
        Money Transfered.
        <button type="button" on:click={hide} class="btn-sm btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
{/if}