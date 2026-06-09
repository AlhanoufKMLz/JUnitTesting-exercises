package com.example.redthreadgame;

import com.example.redthreadgame.Enums.GameSessionStatusType;
import com.example.redthreadgame.Model.GameSession;
import com.example.redthreadgame.Repository.GameSessionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GameSessionRepositoryTest {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    GameSession gameSession, gameSession1, gameSession2, gameSession3;
    List<GameSession> gameSessionList;

    @BeforeEach
    void setUp() {
        gameSession1 = new GameSession(null, GameSessionStatusType.PENDING, true, "123456", 2, 0, 0, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null, null, null,null);
        gameSession2 = new GameSession(null, GameSessionStatusType.PENDING, true, "123457", 1, 2, 0, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null, null, null, null);
        gameSession3 = new GameSession(null, GameSessionStatusType.PENDING, true, "123458", 1, 2, 0, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null, null, null, null);

        gameSessionRepository.save(gameSession1);
        gameSessionRepository.save(gameSession2);
        gameSessionRepository.save(gameSession3);
    }

    @Test
    public void findGameSessionById(){
        gameSession = gameSessionRepository.findGameSessionById(gameSession1.getId());
        Assertions.assertThat(gameSession).isEqualTo(gameSession1);
    }

    @Test
    public void findBySessionCode(){
        gameSession = gameSessionRepository.findBySessionCode(gameSession1.getSessionCode());
        Assertions.assertThat(gameSession.getSessionCode()).isEqualTo(gameSession1.getSessionCode());
    }

    @Test
    public void findByGameSessionStatus(){
        gameSessionList = gameSessionRepository.findAllByStatus(gameSession1.getStatus());
        Assertions.assertThat(gameSessionList.get(0).getStatus()).isEqualTo(gameSession1.getStatus());
    }

}
