<script lang="ts">
    // imports
    import {onMount} from "svelte";
    import Item from './Item.svelte';
    import EmptyItem from './EmptyItem.svelte';
    // variables
    let cart = {
        items: []
    };
    // lifecycle
    onMount(() => {
        queryCart();
    })
    // commands
    // queries
    async function queryCart() {
        //
        let response = await fetch('/api/cart', {
            method: 'GET',
            headers: {
                query: 'QueryCart'
            }
        });
        cart = await response.json();
    }
    // events
    function onItemDeleted(event) {
        console.log(event);
        cart.items = cart.items.filter(item => { return item.product.no != event.detail.prodId});
    }
</script>

<h5>Cart</h5>

<div class="container text-end">
    <button class="btn btn-danger btn-sm">Delete</button>
</div>
<table class="table">
    <thead>
        <tr>
            <th>#</th>
            <th scope="col">Name</th>
            <th scope="col" class="text-end">Price</th>
            <th scope="col">Quantity</th>
            <th scope="col"></th>
            <th scope="col" class="text-end">Total</th>
            <th></th>
        </tr>
    </thead>
    <tbody class="table-group-divider">
    {#if cart.items.length > 0}
        {#each cart.items as item, index}
            <Item cartId={cart.cartId} index={index+1} item={item} on:itemDeleted={onItemDeleted}/>
        {/each}
    {:else}
        <EmptyItem />
    {/if}
    </tbody>
</table>
<div class="container text-end">
    <button class="btn btn-primary btn-sm">Save</button>
</div>