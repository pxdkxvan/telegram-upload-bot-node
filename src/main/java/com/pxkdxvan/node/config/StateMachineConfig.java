package com.pxkdxvan.node.config;

import com.pxkdxvan.node.statemachine.BotEvent;
import com.pxkdxvan.node.statemachine.BotState;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.*;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<BotState, BotEvent> {

    private final JpaStateMachineRepository jpaStateMachineRepository;

    @Bean
    StateMachineRuntimePersister<BotState, BotEvent, String> stateMachineRuntimePersister() {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }

    @Bean
    StateMachineService<BotState, BotEvent> stateMachineService(
            StateMachineFactory<BotState, BotEvent> stateMachineFactory,
            StateMachineRuntimePersister<BotState, BotEvent, String> stateMachineRuntimePersister) {
        return new DefaultStateMachineService<>(stateMachineFactory, stateMachineRuntimePersister);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<BotState, BotEvent> config) throws Exception {
        config.withConfiguration().autoStartup(true);
        config.withPersistence().runtimePersister(stateMachineRuntimePersister());
    }

    @Override
    public void configure(StateMachineStateConfigurer<BotState, BotEvent> states) throws Exception {
        states
                .withStates()
                .initial(BotState.FREE_TO_USE)
                .state(BotState.WAITING_FOR_FILE)
                .state(BotState.WAITING_FOR_EMAIL);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<BotState, BotEvent> transitions) throws Exception {
        transitions
                // if (event == CANCEL) ANY WAITING -> FREE
                .withExternal()
                .source(BotState.WAITING_FOR_EMAIL)
                .target(BotState.FREE_TO_USE)
                .event(BotEvent.CANCEL)
                .and()
                .withExternal()
                .source(BotState.WAITING_FOR_FILE)
                .target(BotState.FREE_TO_USE)
                .event(BotEvent.CANCEL)
                .and()
                // if (event == REGISTRATION || event == LOAD) FREE -> WAITING
                .withExternal()
                .source(BotState.FREE_TO_USE)
                .target(BotState.WAITING_FOR_EMAIL)
                .event(BotEvent.REGISTRATION)
                .and()
                .withExternal()
                .source(BotState.FREE_TO_USE)
                .target(BotState.WAITING_FOR_FILE)
                .event(BotEvent.LOAD)
                .and()
                // if (event == DATA_RECEIVED) WAITING -> FREE
                .withExternal()
                .source(BotState.WAITING_FOR_EMAIL)
                .target(BotState.FREE_TO_USE)
                .event(BotEvent.DATA_RECEIVED)
                .and()
                .withExternal()
                .source(BotState.WAITING_FOR_FILE)
                .target(BotState.FREE_TO_USE)
                .event(BotEvent.DATA_RECEIVED);
    }

}
