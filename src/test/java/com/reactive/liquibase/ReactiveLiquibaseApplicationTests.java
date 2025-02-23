package com.reactive.liquibase;

import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
//import reactor.util.function.Tuple2;

//@SpringBootTest
class ReactiveLiquibaseApplicationTests {

//    @Test
//    void contextLoads() {
//        System.out.println("Start Test..");
//        Mono<String> codeWithAvadhut = Mono.just("Code with Avadhut");
//        Mono<String> justCodeWithAvadhut = Mono.just("subscribe to this Channel");
//        Mono<Mono<String>> zip = Mono.zip(codeWithAvadhut, justCodeWithAvadhut, (s, s2) -> {
////            return s +" : "+ s2;
//            return Mono.just("Hello AJ");
//        });
//        zip.flatMap(data -> data.map(String::toLowerCase)).subscribe(System.out::println);

    /// /        zip.flatMap(s -> s).subscribe(System.out::println);
//        Mono<Tuple2<String, String>> zip1 = Mono.zip(codeWithAvadhut, justCodeWithAvadhut);
//        zip1.flatMap(data -> {
//            return Mono.just(data.getT1().toUpperCase() + " : " + data.getT2());
//        });
//        zip1.subscribe(System.out::println);
//    }
    @Test
    void TestMono1() {
        Mono<String> mono1 = Mono.just("Hello Aj You are Very Excellent Developer. I proud of you");
        Mono<String> stringMono = mono1.flatMap(data ->
                Mono.just(data.replace(".", "!")));
        Mono<String> map = mono1.map(String::toUpperCase);
        StepVerifier.create(stringMono)
                .expectNext("Hello Aj You are Very Excellent Developer! I proud of you")
                .verifyComplete();
        StepVerifier.create(map)
                .expectNext("HELLO AJ YOU ARE VERY EXCELLENT DEVELOPER. I PROUD OF YOU")
                .verifyComplete();


    }
    @Test
    void TestFlux1() {
        Flux<String> flux1 = Flux.just("Hello Aj", "You are Very Excellent Developer.", "I proud of you");
        Flux<String> stringFlux = flux1.flatMap(data ->
                Flux.just(data.replace(".", "!")));
        Flux<String> map = flux1.map(String::toUpperCase);
        StepVerifier.create(stringFlux)
               .expectNext("Hello Aj", "You are Very Excellent Developer!", "I proud of you")
               .verifyComplete();
        StepVerifier.create(map)
               .expectNext("HELLO AJ", "YOU ARE VERY EXCELLENT DEVELOPER.", "I PROUD OF YOU")
               .verifyComplete();

    }

}
