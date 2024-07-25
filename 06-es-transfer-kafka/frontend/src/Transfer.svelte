<script lang="ts">
    // imports components
    import AlertBox from './Alert.svelte';
    // variables
    let fromAccountNo: string = 'b6312d87';
    let toAccountNo: string = 'b4d8a86f';
    let amount: number = 100;
    let timer: NodeJS.Timer;
    let alertBox;
    // commands
    function initialize(element) {
        element.focus();
    }
    async function transferMoney() {
        console.log(fromAccountNo + ' -> ' + toAccountNo + ': ' + amount);

        const response = await fetch('/api/transfer', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'service': 'transfer',
                'command': 'TransferMoney'
            },
            body: JSON.stringify({
                fromAccountNo: fromAccountNo,
                toAccountNo: toAccountNo,
                amount: amount
            })
        });
        let transferId: string = await response.text();
        timer = setInterval(function() {
            console.log("Timer Started.");
            completeTransfer(transferId);
        }, 500);
    }
    async function completeTransfer(transferId: string) {
        const response = await fetch('/api/transfer/' + transferId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'service': 'transfer',
                'query': 'QueryTransfer'
            }
        });
        let transfer = await response.json();
        if (transfer.state == 'Succeed' || transfer.state == 'Fail') {
            console.log("Timer Stopped.");
            clearInterval(timer);
            alertBox.show();
        }
    }
</script>

<h4>Transfer Money</h4>

<AlertBox bind:this={alertBox}></AlertBox>

<div class="mb-2 row">
    <div class="col position-relative">
        <label for="fromAccount" class="form-label">From Account</label>
        <input bind:value={fromAccountNo} type="text" use:initialize class="form-control form-control-sm" id="fromAccount" placeholder="From Account">
    </div>
    <div class="col position-relative">
        <label for="toAccount" class="form-label">To Account</label>
        <input bind:value={toAccountNo} type="text" class="form-control form-control-sm" id="toAccount" placeholder="toAccount">
    </div>
</div>
<div class="mb-2">
    <label for="amount" class="form-label">Amount</label>
    <input bind:value={amount} type="number" class="form-control form-control-sm" id="amount" placeholder="Amount">
</div>
<div class="mb-2 text-md-end">
    <button type="button" class="btn btn-primary btn-sm" on:click={transferMoney}>Transfer</button>
</div>