<script lang="ts">
  //
  import { Router, Route } from 'svelte-navigator';
  // imports components
  import Header from './Header.svelte';
  import Transfer from './Transfer.svelte';
</script>

<Header />

<Router>
  <Route path="/transfer">
    <div class="container-xxl">
      <div class="container flex-row col-12" style="float: none; margin:0 auto; padding-top: 10px;">
        <main>
          <Transfer />
        </main>
      </div>
    </div>
  </Route>
  <Route path="/">
    <div class="container-xxl">
      <div class="container flex-row col-12" style="float: none; margin:0 auto; padding-top: 10px;">
        <main>
          <Transfer />
        </main>
      </div>
    </div>
  </Route>
</Router>