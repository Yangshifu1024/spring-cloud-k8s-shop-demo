package com.mrheaaaavy.demo.shop.member.handler;

import com.mrheaaaavy.demo.shop.member.response.Member;
import com.mrheaaaavy.demo.shop.member.response.MemberDetailResponse;
import com.mrheaaaavy.demo.shop.member.response.MemberListResponse;
import com.mrheaaaavy.demo.shop.trade.client.TradeClient;
import com.mrheaaaavy.demo.shop.trade.response.TradeListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mrheaaaavy
 */
@RestController
@RequestMapping("/members")
public class MemberHandler {

    private final TradeClient tradeClient;

    public MemberHandler(TradeClient tradeClient) {
        this.tradeClient = tradeClient;
    }

    @GetMapping("/detail")
    public Mono<MemberDetailResponse> detail() {
        TradeListResponse tradeListResponse = tradeClient.list(1, 18);

        var resp = MemberDetailResponse.builder()
                .member(new Member("member#1"))
                .trades(tradeListResponse.getTrades())
                .build();

        return Mono.just(resp);
    }

    @GetMapping("")
    public Mono<MemberListResponse> list() {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            members.add(new Member(String.format("member#%d", i)));
        }

        var resp = new MemberListResponse()
                .setMembers(members)
                .setPage(0)
                .setSize(0)
                .setTotal(0);

        return Mono.just(resp);
    }

}
