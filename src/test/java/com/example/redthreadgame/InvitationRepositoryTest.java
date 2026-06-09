package com.example.redthreadgame;

import com.example.redthreadgame.Enums.GameSessionStatusType;
import com.example.redthreadgame.Enums.InvitationStatusType;
import com.example.redthreadgame.Model.GameSession;
import com.example.redthreadgame.Model.Invitation;
import com.example.redthreadgame.Model.Player;
import com.example.redthreadgame.Repository.GameSessionRepository;
import com.example.redthreadgame.Repository.InvitationRepository;
import com.example.redthreadgame.Repository.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InvitationRepositoryTest {

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;


    Invitation invitation, invitation1, invitation2, invitation3;
    Player player, player2, player3;
    GameSession gameSession1, gameSession2, gameSession3;
    List<Invitation> invitationList;

    @BeforeEach
    void setUp() {
        player = new Player(null, "alhanouf", "hnf", "alhanouf@gmail.com", "+9667507284", "12345678", 27,  0, null, null, null, null, null, null, null, null);
        playerRepository.save(player);

        gameSession1 = new GameSession(null, GameSessionStatusType.PENDING, true, "126456", 2, 0, 0, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null, null, null,null);
        gameSession2 = new GameSession(null, GameSessionStatusType.PENDING, true, "116456", 2, 0, 0, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null, null, null,null);
        gameSession3 = new GameSession(null, GameSessionStatusType.PENDING, true, "116556", 2, 0, 0, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null, null, null,null);
        gameSessionRepository.save(gameSession1);
        gameSessionRepository.save(gameSession2);
        gameSessionRepository.save(gameSession3);

        invitation1 = new Invitation(null, InvitationStatusType.PENDING, gameSession1, player);
        invitation2 = new Invitation(null, InvitationStatusType.PENDING, gameSession2, player);
        invitation3 = new Invitation(null, InvitationStatusType.PENDING, gameSession3, player);
        invitationRepository.saveAll(List.of(invitation1, invitation2, invitation3));
    }


    @Test
    public void findInvitationById(){
        invitation = invitationRepository.findInvitationById(invitation1.getId());
        Assertions.assertThat(invitation).isEqualTo(invitation1);
    }

    @Test
    void findAllByPlayerId() {
        invitationList = invitationRepository.findAllByPlayerId(player.getId());
        Assertions.assertThat(invitationList.size()).isEqualTo(3);
    }

    @Test
    void findByGameSessionIdAndPlayerId(){
        invitation = invitationRepository.findByGameSessionIdAndPlayerId(invitation1.getGameSession().getId(), invitation1.getPlayer().getId());
        Assertions.assertThat(invitation).isEqualTo(invitation1);
    }
}
