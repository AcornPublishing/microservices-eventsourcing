<script lang="ts">
    //
    import History from './History.svelte';
    // variables
    let history;
    //
    let notfound = false;
    let accountNo: String = null;
    let depositAmount: number = 0;
    let withdrawAmount: number = 0;
    let account = null
    //
    function initialize(element) {
        element.focus();
    }
    async function search() {
        let response = await fetch('/api/account/' + accountNo, {
            method: 'GET',
            headers: {
                'service': 'account',
                'query': 'QueryAccount'
            }
        });
        if (response.status == 404) {
            notfound = true;
        } else {
            notfound = false;
            account = await response.json();
            history.queryHistory();
        }
    }
    async function deposit() {
        let response = await fetch('/api/account/' + accountNo, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'service': 'account',
                'command': 'Deposit'
            },
            body: JSON.stringify({
                amount: depositAmount
            })
        });
        if (response.status == 200) {
            depositAmount = 0;
            search();
        }
    }
    async function withdraw() {
        let response = await fetch('/api/account/' + accountNo, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'service': 'account',
                'command': 'Withdraw'
            },
            body: JSON.stringify({
                amount: withdrawAmount
            })
        });
        if (response.status == 200) {
            withdrawAmount = 0;
            search();
        }
    }
</script>

<h4>Account</h4>

<div class="mb-2 row">
    {#if notfound}
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Account(accountNo) is not found.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    {/if}
    <div class="col-10 position-relative">
        <input bind:value={accountNo} type="text" use:initialize class="form-control form-control-sm" id="accountNo" placeholder="Account Number">
    </div>
    <div class="col-2">
        <button on:click={search} class="btn btn-primary btn-sm">Search</button>
    </div>
</div>
{#if account}
<div class="mb-2 row">
    <label class="form-label">Balance: {account.balance}</label>
</div>
{/if}

<h5>Deposit</h5>
<div class="mb-2 row">
    <div class="col-10 position-relative">
        <input bind:value={depositAmount} type="text" class="form-control form-control-sm" placeholder="Deposit Amount">
    </div>
    <div class="col-2">
        <button on:click={deposit} class="btn btn-success btn-sm">Deposit</button>
    </div>
</div>

<h5>Withdraw</h5>
<div class="mb-2 row">
    <div class="col-10 position-relative">
        <input bind:value={withdrawAmount} type="text" class="form-control form-control-sm" placeholder="Deposit Amount">
    </div>
    <div class="col-2">
        <button on:click={withdraw} class="btn btn-warning btn-sm">Withdraw</button>
    </div>
</div>

<History bind:this={history} accountNo="{accountNo}" />

