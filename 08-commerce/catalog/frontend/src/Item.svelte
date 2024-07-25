<script lang="ts">
    // imports
    import { createEventDispatcher } from 'svelte';
    // exports
    export let index;
    export let item;
    // variables
    const dispatch = createEventDispatcher();
    // commands
    function deleteItem() {
        dispatch('itemDeleted', {
            prodId: item.prodId
        });
    }
</script>

<tr>
    <td>{index}</td>
    <td>{item.prodName}</td>
    <td>{item.price.toLocaleString('ko-KR')}</td>
    <td>{item.quantity}</td>
    <td><button class="btn btn-danger btn-sm" on:click={deleteItem}>Delete</button></td>
</tr>