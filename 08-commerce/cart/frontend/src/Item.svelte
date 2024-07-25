<script lang="ts">
    // imports
    import {createEventDispatcher, onMount} from 'svelte';
    // exports
    export let index;
    export let cartId;
    export let item;
    // variables
    const dispatch = createEventDispatcher();
    let changedQuantity = item.quantity;
    // lifecycle
    onMount(() => {
    });
    // commands
    async function deleteItem() {
        let response = await fetch('/api/cart', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'command': 'RemoveItem'
            },
            body: JSON.stringify({
                cartId: cartId,
                productNo: item.product.no,
            })
        });
        if (response.status == 200) {
            dispatch('itemDeleted', {
                prodId: item.product.no
            });
        }
    }
    // event handlers
    async function onQuantityChanged() {
        let response = await fetch('/api/cart', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'command': 'ChangeQuantity'
            },
            body: JSON.stringify({
                cartId: cartId,
                productNo: item.product.no,
                quantity: changedQuantity
            })
        });
        if (response.status == 200) {
            item.quantity = changedQuantity;
        }
    }
</script>

<tr>
    <td class="align-middle" style="width: 50px;">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
        </div>
    </td>
    <td class="align-middle">{item.product.name}</td>
    <td class="align-middle text-end" style="width: 120px;">{item.product.price.toLocaleString('ko-KR')}</td>
    <td class="align-middle" style="width: 80px;">
        <input type="text" class="form-control form-control-sm text-center" bind:value={changedQuantity} on:blur={onQuantityChanged} />
    </td>
    <td class="align-middle" style="width: 80px;">
        <button class="btn btn-outline-secondary btn-sm">Option</button>
    </td>
    <td class="align-middle text-end" style="width: 110px;">
        {(item.product.price * item.quantity).toLocaleString('ko-KR')}
    </td>
    <td class="align-middle" style="width: 80px;">
        <button class="btn btn-warning btn-sm" on:click={deleteItem}>Delete</button>
    </td>
</tr>