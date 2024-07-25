<script lang="ts">
    //
    export let accountNo: string;
    let events = [];
    //
    export async function queryHistory() {
        let response = await fetch('/api/account/' + accountNo + '/event', {
            method: 'GET',
            headers: {
                'service': 'account',
                'query': 'QueryEvents'
            }
        });
        events = await response.json();
    }
</script>

{#if accountNo}
    <h5>History</h5>
    <ul class="timeline">
        {#each events as item}
        <li>
            {#if item.type == 'AccountOpened'}
                <p>{item.type} - {item.balance}</p>
            {:else if item.type == 'Deposited' || item.type == 'Withdrawed'}
                <p>{item.type} - {item.amount}</p>
            {:else if item.type == 'AccountClosed'}
                <p>{item.type}</p>
            {/if}
        </li>
        {/each}
    </ul>
{/if}