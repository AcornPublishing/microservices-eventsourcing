<script lang="ts">
    // imports
    import {onMount} from "svelte";
    import Category from './Category.svelte';
    // variables
    let items = [
        {
            prodId: "1234",
            prodName: "Macbook Pro 16'",
            price: 4310000
        },
        {
            prodId: "1235",
            prodName: "iPad Pro",
            price: 760000
        },
        {
            prodId: "1236",
            prodName: "Apple Watch SE",
            price: 431000
        },
        {
            prodId: "1237",
            prodName: "Apple Watch",
            price: 680000
        }
    ];
    // lifecycle
    onMount(() => {
        items.forEach(item => {
            let element = document.getElementById("additem-" + item.prodId);
            element.setAttribute("itemjson", JSON.stringify(item));
        });

        let cartItems = document.getElementsByTagName("cart-items")[0];
        window.addEventListener('itemadded', (event) => {
            cartItems.reload();
        })
    })
    // events
    function onItemDeleted(event) {
        items = items.filter(item => { return item.prodId != event.detail.prodId});
    }
</script>

<div class="container-xxl">
    <div class="container flex-row col-12" style="float: none; margin:0 auto; padding-top: 10px;">
        <main class="bd-main">
            <div class="bd-content">
                <h5>Catalog</h5>

<!--                <div class="row" style="padding: 10px;">-->
<!--                    <Category />-->
<!--                </div>-->

                <div class="row">
                {#each items as item}
                    <div class="col-sm-3" style="margin: 10px; width: 30%;">
                        <div class="card" style="width: 18rem;">
                            <svg class="bd-placeholder-img card-img-top" width="100%" height="180" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Image cap" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#868e96"></rect><text x="50%" y="50%" fill="#dee2e6" dy=".3em">Image cap</text></svg>

                            <div class="card-body">
                                <h5 class="card-title">{item.prodName}</h5>
                                <p class="card-text">{item.price.toLocaleString('ko-KR')}</p>
                                <cart-additemtocart id="additem-{item.prodId}"></cart-additemtocart>
                            </div>
                        </div>
                    </div>
                {/each}
                </div>
            </div>

            <div class="bd-toc mt-3 mb-5 my-lg-0 ps-xl-3 mb-lg-5 text-muted" style="margin-left: 30px;">
                <cart-items></cart-items>
            </div>
        </main>
    </div>
</div>



