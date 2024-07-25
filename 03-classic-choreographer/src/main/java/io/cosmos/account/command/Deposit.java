package io.cosmos.account.command;

import io.cosmos.core.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.text.html.Option;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Deposit extends Command {
    //
    private String no;
    private String fromAccountNo;
    private int amount;
    //
    private Optional<String> transferId = Optional.empty();
}
