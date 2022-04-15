package com.hs.blog.config;


import com.hs.blog.constant.MessageConstant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConf {
    /**
     *  the configuration for RabbitMQ
     *  including : config queues exchanges and bind them together
     * */

    @Bean(MessageConstant.EXCHANGE_DIRECT)
    public Exchange EXCHANGE_DIRECT() {
        // declare an exchange for view count task
        // type :: DIRECT
        // durable(true) :: after restart rabbitmq this queue will still be there
        return ExchangeBuilder.directExchange(MessageConstant.EXCHANGE_DIRECT).durable(true).build();
    }

    @Bean(MessageConstant.QUEUE_BLOG_REFRESH_VIEW)
    public Queue QUEUE_BLOG_VIEW() {
        // declare a queue for view count task
        return new Queue(MessageConstant.QUEUE_BLOG_REFRESH_VIEW);
    }

    @Bean
    public Binding BINDING_QUEUE_BLOG_VIEW(@Qualifier(MessageConstant.QUEUE_BLOG_REFRESH_VIEW) Queue queue, @Qualifier(MessageConstant.EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MessageConstant.ROUTING_KEY_REFRESH_VIEW).noargs();
    }

    @Bean(MessageConstant.QUEUE_BLOG_REFRESH_EDITED)
    public Queue QUEUE_BLOG_REFRESH_EDITED() {
        // declare a queue for refresh edited article
        return new Queue(MessageConstant.QUEUE_BLOG_REFRESH_EDITED);
    }

    @Bean
    public Binding BINDING_QUEUE_BLOG_REFRESH_EDITED(@Qualifier(MessageConstant.QUEUE_BLOG_REFRESH_EDITED) Queue queue, @Qualifier(MessageConstant.EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MessageConstant.ROUTING_KEY_REFRESH_VIEW).noargs();
    }



}
