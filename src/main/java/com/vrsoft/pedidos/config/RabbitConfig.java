package com.vrsoft.pedidos.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.queues.entrada}")
    private String entradaQueue;

    @Value("${app.queues.entradaDlq}")
    private String entradaDlq;

    @Value("${app.queues.statusSucesso}")
    private String statusSucessoQueue;

    @Value("${app.queues.statusFalha}")
    private String statusFalhaQueue;

    @Bean
    public MessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf, MessageConverter mc) {
        RabbitTemplate template = new RabbitTemplate(cf);
        template.setMessageConverter(mc);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory cf, MessageConverter mc) {
        SimpleRabbitListenerContainerFactory f = new SimpleRabbitListenerContainerFactory();
        f.setConnectionFactory(cf);
        f.setMessageConverter(mc);
        f.setDefaultRequeueRejected(false);
        return f;
    }

    @Bean
    public DirectExchange entradaDlx() {
        return new DirectExchange(entradaQueue + ".dlx");
    }

    @Bean
    public Queue pedidosEntradaQueue() {
        return QueueBuilder.durable(entradaQueue)
                .withArgument("x-dead-letter-exchange", entradaQueue + ".dlx")
                .withArgument("x-dead-letter-routing-key", "dlq")
                .build();
    }

    @Bean
    public Queue pedidosEntradaDlq() {
        return QueueBuilder.durable(entradaDlq).build();
    }

    @Bean
    public Binding bindDlq() {
        return BindingBuilder.bind(pedidosEntradaDlq()).to(entradaDlx()).with("dlq");
    }

    @Bean
    public Queue statusSucessoQueue() {
        return QueueBuilder.durable(statusSucessoQueue).build();
    }

    @Bean
    public Queue statusFalhaQueue() {
        return QueueBuilder.durable(statusFalhaQueue).build();
    }
}
