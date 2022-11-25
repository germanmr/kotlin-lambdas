package com.spacexdata.api.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.ProducerListener


@Configuration
class KafkaConsumerConfig {
    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> {
        val props: Map<String, Any> = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to
                    "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to
                    "simple-kotlin-consumer",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to
                    StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to
                    StringDeserializer::class.java
        )
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

    @Bean
    fun kafkaTemplate(
        kafkaProducerFactory: ProducerFactory<String, String>,
        consumerFactory: ConsumerFactory<String, String>
    ): KafkaTemplate<String, String> {
        val kafkaTemplate: KafkaTemplate<String, String> = KafkaTemplate(kafkaProducerFactory)
        kafkaTemplate.setConsumerFactory(consumerFactory)
        return kafkaTemplate
    }
}