<script lang="ts">
  //
  import { Router, Route } from 'svelte-navigator';
  // imports components
  import Header from './Header.svelte';
  import Cart from './Cart.svelte';
  import {onMount} from "svelte";

  onMount(() => {
  })
</script>

<Header />

<Router>
  <Route path="/">
    <div class="container-xxl">
      <div class="container flex-row col-12" style="float: none; margin:0 auto; padding-top: 10px;">
        <main>
          <Cart />
        </main>
      </div>
    </div>
  </Route>
  <Route path="/cart">
    <div class="container-xxl">
      <div class="container flex-row col-12" style="float: none; margin:0 auto; padding-top: 10px;">
        <main>
          <Cart />
        </main>
      </div>
    </div>
  </Route>
</Router>