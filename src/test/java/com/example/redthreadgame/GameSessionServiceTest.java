package com.example.redthreadgame;

import com.example.redthreadgame.DTO.IN.GameSessionIn;
import com.example.redthreadgame.DTO.OUT.GameSessionOut;
import com.example.redthreadgame.Enums.GameSessionStatusType;
import com.example.redthreadgame.Model.Case;
import com.example.redthreadgame.Model.GameSession;
import com.example.redthreadgame.Model.Player;
import com.example.redthreadgame.Repository.CaseRepository;
import com.example.redthreadgame.Repository.GameSessionRepository;
import com.example.redthreadgame.Repository.InvitationRepository;
import com.example.redthreadgame.Repository.PlayerRepository;
import com.example.redthreadgame.Service.GameSessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.redthreadgame.Enums.GameSessionStatusType.PENDING;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameSessionServiceTest {

    @InjectMocks
    GameSessionService gameSessionService;
    @Mock
    GameSessionRepository gameSessionRepository;
    @Mock
    ModelMapper modelMapper;

    GameSession gameSession1, gameSession2, gameSession3;
    List<GameSession> gameSessionList;
    Player player;
    Case case1;

    @BeforeEach
    void setUp() {
        gameSession1 = new GameSession(null, GameSessionStatusType.PENDING, false, "123447", 1, 2, 0, LocalDateTime.now(), LocalDateTime.now(), case1, null, null, null, null, null, null, null);
        gameSession2 = new GameSession(null, GameSessionStatusType.PENDING, false, "123457", 1, 2, 0, LocalDateTime.now(), LocalDateTime.now(), case1, null, null, null, null, null, null, null);
        gameSession3 = new GameSession(null, GameSessionStatusType.PENDING, true, "123458", 1, 2, 0, LocalDateTime.now(), LocalDateTime.now(), case1, null, null, null, null, null, null, null);
        gameSessionList =  List.of(gameSession1, gameSession2, gameSession3);

        player = new Player(null, "alhanouf", "hnf", "alhanouf@gmail.com", "+9667507284", "12345678", 27,  0, null, null, null, null, null, null, null, null);
        case1 = new Case(null, "titile", "scenario", "HARD", "PUBLISHED", null, null, null, null, null);

    }

    @Test
    void getAllGameSessions() {
        GameSessionOut out1 = new GameSessionOut();
        GameSessionOut out2 = new GameSessionOut();
        GameSessionOut out3 = new GameSessionOut();

        when(gameSessionRepository.findAll()).thenReturn(gameSessionList);
        when(modelMapper.map(gameSession1, GameSessionOut.class)).thenReturn(out1);
        when(modelMapper.map(gameSession2, GameSessionOut.class)).thenReturn(out2);
        when(modelMapper.map(gameSession3, GameSessionOut.class)).thenReturn(out3);

        List<GameSessionOut> gameSessions = gameSessionService.getAllGameSessions();

        Assertions.assertEquals(3, gameSessions.size());
        verify(gameSessionRepository, times(1)).findAll();
        verify(modelMapper, times(3)).map(any(GameSession.class), eq(GameSessionOut.class));
    }

    @Test
    void updateGameSession() {
        GameSessionIn gameSessionIn = new GameSessionIn();
        gameSessionIn.setIsPrivate(gameSession1.getIsPrivate());
        gameSessionIn.setPlayersCount(gameSession1.getPlayersCount());

        when(gameSessionRepository.findGameSessionById(gameSession1.getId())).thenReturn(gameSession1);

        gameSessionService.updateGameSession(gameSession1.getId(), gameSessionIn);

        verify(gameSessionRepository, times(1)).findGameSessionById(gameSession1.getId());
        verify(gameSessionRepository, times(1)).save(any(GameSession.class));
    }

    @Test
    void deleteGameSession(){
        when(gameSessionRepository.findGameSessionById(gameSession1.getId())).thenReturn(gameSession1);

        gameSessionService.deleteGameSession(gameSession1.getId());

        verify(gameSessionRepository,times(1)).findGameSessionById(gameSession1.getId());
        verify(gameSessionRepository,times(1)).delete(gameSession1);
    }

    @Test
    void getPublicGameSessions() {
        when(gameSessionRepository.findAllByIsPrivateFalse()).thenReturn(gameSessionList);
        when(modelMapper.map(gameSession1, GameSessionOut.class)).thenReturn(new GameSessionOut());
        when(modelMapper.map(gameSession2, GameSessionOut.class)).thenReturn(new GameSessionOut());

        List<GameSessionOut> result = gameSessionService.getPublicGameSessions();
        Assertions.assertEquals(3, result.size());

        verify(gameSessionRepository, times(1)).findAllByIsPrivateFalse();
        verify(modelMapper, times(3)).map(any(GameSession.class), eq(GameSessionOut.class));
        verify(modelMapper, never()).map(eq(gameSessionList), eq(GameSessionOut.class));
    }

    @Test
    void getPublicGameSessionsByCase() {
        when(gameSessionRepository.findAllByIsPrivateFalseAndSessionCaseIdAndStatus(case1.getId(), PENDING)).thenReturn(gameSessionList);
        when(modelMapper.map(gameSession1, GameSessionOut.class)).thenReturn(new GameSessionOut());
        when(modelMapper.map(gameSession2, GameSessionOut.class)).thenReturn(new GameSessionOut());

        List<GameSessionOut> result = gameSessionService.getPublicGameSessionsByCase(case1.getId());
        Assertions.assertEquals(3, result.size());

        verify(gameSessionRepository, times(1)).findAllByIsPrivateFalseAndSessionCaseIdAndStatus(case1.getId(), PENDING);
        verify(modelMapper, times(3)).map(any(GameSession.class), eq(GameSessionOut.class));
        verify(modelMapper, never()).map(eq(gameSessionList), eq(GameSessionOut.class));
    }

}
