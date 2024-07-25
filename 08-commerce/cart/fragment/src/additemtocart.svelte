<script lang="ts">
    //
    export let itemjson;
    let item = itemjson ? JSON.parse(itemjson) : {};
    //
    async function additemtocart(event) {
        event.preventDefault();

        let response = await fetch('/api/cart', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'command': 'AddItem'
            },
            body: JSON.stringify({
                cartId: 'id9xf8w1',
                productNo: item.prodId,
                productName: item.prodName,
                price: item.price,
                quantity: 1
            })
        })

        dispatchEvent(new CustomEvent('itemadded', {
            detail: { prodId: item.prodId}
        }));
    }
</script>

<svelte:options tag="cart-additemtocart" accessors={true} />

<button class="btn btn-primary btn-sm" on:click={additemtocart}>Add to Cart ({item.prodId})</button>

<style>
    .btn {
        display: inline-block;
        font-weight: 400;
        line-height: 1.5;
        color: #212529;
        text-align: center;
        text-decoration: none;
        vertical-align: middle;
        cursor: pointer;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
        background-color: transparent;
        border: 3px solid transparent;
        padding: .375rem .75rem;
        font-size: 1rem;
        border-radius: .25rem;
        transition: color .15s ease-in-out, background-color .15s ease-in-out, border-color .15s ease-in-out, box-shadow .15s ease-in-out
    }

    @media (prefers-reduced-motion:reduce) {
        .btn {
            transition: none
        }
    }

    .btn:hover {
        color: #212529
    }

    .btn-check:focus+.btn,
    .btn:focus {
        outline: 0;
        box-shadow: 0 0 0 .25rem rgba(13, 110, 253, .25)
    }

    .btn.disabled,
    .btn:disabled,
    fieldset:disabled .btn {
        pointer-events: none;
        opacity: .65
    }

    .btn-primary {
        color: #fff;
        background-color: #0d6efd;
        border-color: #0d6efd
    }

    .btn-primary:hover {
        color: #fff;
        background-color: #0b5ed7;
        border-color: #0a58ca
    }

    .btn-check:focus+.btn-primary,
    .btn-primary:focus {
        color: #fff;
        background-color: #0b5ed7;
        border-color: #0a58ca;
        box-shadow: 0 0 0 .25rem rgba(49, 132, 253, .5)
    }

    .btn-sm {
        padding: .25rem .5rem;
        font-size: .875rem;
        border-radius: .2rem
    }
</style>