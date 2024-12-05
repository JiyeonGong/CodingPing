package com.codingping.controller;

import com.codingping.dto.HelpRequest;
import com.codingping.dto.HelpResponse;
import com.codingping.dto.HelpUpdateRequest;
import com.codingping.entity.Help;
import com.codingping.service.HelpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/helps")
public class HelpController {
    private final HelpService helpService;

    @PostMapping
    public ResponseEntity<Help> createHelp(@RequestBody HelpRequest helpRequest) {
        log.info("POST /helps 요청 수신");
        Help createdHelp = helpService.createHelpRequest(helpRequest);

        if (helpRequest.getUserId() == null) {
            throw new IllegalArgumentException("User ID must not be null.");
        }
        return ResponseEntity.ok(createdHelp);
    }

    @GetMapping("/list")
    public ResponseEntity<List<HelpResponse>> getHelpsByKakaoId(@RequestParam Long kakaoId) {
        List<HelpResponse> helps = helpService.getHelpsByKakaoId(kakaoId);
        return ResponseEntity.ok(helps);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHelp(@PathVariable Long id) {
        try {
            helpService.deleteHelpById(id);
            return ResponseEntity.ok("문의가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("해당 ID의 문의를 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("문의 삭제 중 오류가 발생했습니다.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateHelp(@PathVariable Long id, @RequestBody HelpUpdateRequest request) {
        try {
            helpService.updateHelp(id, request);
            return ResponseEntity.ok("문의가 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("해당 ID의 문의를 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("문의 수정 중 오류가 발생했습니다.");
        }
    }
}
