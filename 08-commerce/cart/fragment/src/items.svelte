<script lang="ts">
    // variables
    import {onMount} from "svelte";
    // variables
    let cart = {};
    // lifecycle
    onMount(() => {
        reload();
    })
    // commands
    export async function reload() {
        let response = await fetch('/api/cart', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                query: 'QueryCart'
            }
        });
        cart = await response.json();
    }
    async function deleteItem(prodNo) {
        let response = await fetch('/api/cart', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                command: 'RemoveItem'
            },
            body: JSON.stringify({
                productNo: prodNo
            })
        });
        let resultCode = response.status;
        if (resultCode == 200) {
            reload();
        }
    }
</script>

<svelte:options tag="cart-items" accessors={true} />

{#if cart && cart.items}
{#each cart.items as item}
    <div class="col-sm-6" style="margin: 10px; width: 30%;">
        <div class="card" style="width: 10rem;">
            <svg class="bd-placeholder-img card-img-top" width="100%" height="80" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Image cap" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#868e96"></rect><text x="30%" y="50%" fill="#dee2e6" dy=".3em">Image cap</text></svg>
            <div class="card-body" style="padding: 5px;">
                <h5 class="card-title" style="margin: 5px;">{item.product.name}</h5>
                <p class="card-text" style="margin: 5px;">{item.product.price.toLocaleString('ko-KR')}</p>
                <button class="btn btn-danger btn-sm" on:click={() => { deleteItem(item.product.no); }}>Delete</button>
            </div>
        </div>
    </div>
{/each}
{/if}

<style>
    .card {
        position: relative;
        display: flex;
        flex-direction: column;
        min-width: 0;
        word-wrap: break-word;
        background-color: #fff;
        background-clip: border-box;
        border: 1px solid rgba(0, 0, 0, .125);
        border-radius: .25rem
    }

    .card>hr {
        margin-right: 0;
        margin-left: 0
    }

    .card>.list-group {
        border-top: inherit;
        border-bottom: inherit
    }

    .card>.list-group:first-child {
        border-top-width: 0;
        border-top-left-radius: calc(.25rem - 1px);
        border-top-right-radius: calc(.25rem - 1px)
    }

    .card>.list-group:last-child {
        border-bottom-width: 0;
        border-bottom-right-radius: calc(.25rem - 1px);
        border-bottom-left-radius: calc(.25rem - 1px)
    }

    .card>.card-header+.list-group,
    .card>.list-group+.card-footer {
        border-top: 0
    }

    .card-body {
        flex: 1 1 auto;
        padding: 1rem 1rem
    }

    .card-title {
        margin-bottom: .5rem
    }

    .card-subtitle {
        margin-top: -.25rem;
        margin-bottom: 0
    }

    .card-text:last-child {
        margin-bottom: 0
    }

    .card-link+.card-link {
        margin-left: 1rem
    }

    .card-header {
        padding: .5rem 1rem;
        margin-bottom: 0;
        background-color: rgba(0, 0, 0, .03);
        border-bottom: 1px solid rgba(0, 0, 0, .125)
    }

    .card-header:first-child {
        border-radius: calc(.25rem - 1px) calc(.25rem - 1px) 0 0
    }

    .card-footer {
        padding: .5rem 1rem;
        background-color: rgba(0, 0, 0, .03);
        border-top: 1px solid rgba(0, 0, 0, .125)
    }

    .card-footer:last-child {
        border-radius: 0 0 calc(.25rem - 1px) calc(.25rem - 1px)
    }

    .card-header-tabs {
        margin-right: -.5rem;
        margin-bottom: -.5rem;
        margin-left: -.5rem;
        border-bottom: 0
    }

    .card-header-pills {
        margin-right: -.5rem;
        margin-left: -.5rem
    }

    .card-img-overlay {
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        padding: 1rem;
        border-radius: calc(.25rem - 1px)
    }

    .card-img,
    .card-img-bottom,
    .card-img-top {
        width: 100%
    }

    .card-img,
    .card-img-top {
        border-top-left-radius: calc(.25rem - 1px);
        border-top-right-radius: calc(.25rem - 1px)
    }

    .card-img,
    .card-img-bottom {
        border-bottom-right-radius: calc(.25rem - 1px);
        border-bottom-left-radius: calc(.25rem - 1px)
    }

    .card-group>.card {
        margin-bottom: .75rem
    }

    @media (min-width:576px) {
        .card-group {
            display: flex;
            flex-flow: row wrap
        }
        .card-group>.card {
            flex: 1 0 0%;
            margin-bottom: 0
        }
        .card-group>.card+.card {
            margin-left: 0;
            border-left: 0
        }
        .card-group>.card:not(:last-child) {
            border-top-right-radius: 0;
            border-bottom-right-radius: 0
        }
        .card-group>.card:not(:last-child) .card-header,
        .card-group>.card:not(:last-child) .card-img-top {
            border-top-right-radius: 0
        }
        .card-group>.card:not(:last-child) .card-footer,
        .card-group>.card:not(:last-child) .card-img-bottom {
            border-bottom-right-radius: 0
        }
        .card-group>.card:not(:first-child) {
            border-top-left-radius: 0;
            border-bottom-left-radius: 0
        }
        .card-group>.card:not(:first-child) .card-header,
        .card-group>.card:not(:first-child) .card-img-top {
            border-top-left-radius: 0
        }
        .card-group>.card:not(:first-child) .card-footer,
        .card-group>.card:not(:first-child) .card-img-bottom {
            border-bottom-left-radius: 0
        }
    }

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
        border: 1px solid transparent;
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

    .btn-danger {
        color: #fff;
        background-color: #dc3545;
        border-color: #dc3545
    }

    .btn-danger:hover {
        color: #fff;
        background-color: #bb2d3b;
        border-color: #b02a37
    }

    .btn-check:focus+.btn-danger,
    .btn-danger:focus {
        color: #fff;
        background-color: #bb2d3b;
        border-color: #b02a37;
        box-shadow: 0 0 0 .25rem rgba(225, 83, 97, .5)
    }

    .btn-sm {
        padding: .25rem .5rem;
        font-size: .875rem;
        border-radius: .2rem
    }
</style>