package info.thale.apiservice;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;


public class ReactiveTest {

    @Test
    void test() {

        Mono.just("test").delayElement(Duration.ofSeconds(3)).map(item -> {
            System.out.println("HELLO");
            return item;
        }).subscribe();

        Mono.just("test").delayElement(Duration.ofSeconds(1)).map(item -> {
            System.out.println("WORLD");
            return item;
        }).subscribe();

        Mono.just("Test")
                .delayElement(Duration.ofSeconds(3))
                .map(item -> {
                    System.out.println("First");
                    return item;
                }).flatMap(item -> Mono.just("second").delayElement(Duration.ofSeconds(5)).map(testt -> {
                    System.out.println("Second");
                    return testt;
                }).flatMap(test -> Mono.just("Third").delayElement(Duration.ofSeconds(3)).map(testtt -> {
                    System.out.println("Third");
                    return testtt;
                }))).block();
    }

}
