package io.cosmos.transfer.store;

import io.cosmos.transfer.aggregate.Transfer;
import io.cosmos.transfer.store.jpa.TransferJpo;
import io.cosmos.transfer.store.jpa.TransferRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class TransferStore {
    //
    private final TransferRepository transferRepository
            ;
    public TransferStore(TransferRepository transferRepository) {
        //
        this.transferRepository = transferRepository;
    }

    public void create(Transfer transfer) {
        //
        this.transferRepository.save(new TransferJpo(transfer));
    }

    public Transfer retrieve(String transferId) {
        //
        Optional<TransferJpo> jpo = this.transferRepository.findById(transferId);
        if (jpo.isEmpty()) {
            throw new NoSuchElementException();
        }
        return jpo.get().toTransfer();
    }

    public void update(Transfer transfer) {
        //
        this.transferRepository.save(new TransferJpo(transfer));
    }
}
