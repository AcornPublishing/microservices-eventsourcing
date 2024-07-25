<script lang="ts">
  //
  import { Router, Route } from 'svelte-navigator';
  // imports components
  import Header from './Header.svelte';
  import Catalog from './Catalog.svelte';
</script>

<Header />

<Router>
  <Route path="/">
    <Catalog />
  </Route>
  <Route path="/catalog">
    <Catalog />
  </Route>
</Router>