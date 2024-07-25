package io.cosmos.account.command;

import io.cosmos.core.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Withdraw extends Command {
    //
    private String no;
    private int amount;
    //
    private Optional<String> transferId = Optional.empty();
}
