package com.woowacourse.levellog.authentication.presentation;

import com.woowacourse.levellog.authentication.application.OAuthService;
import com.woowacourse.levellog.authentication.dto.GithubCodeDto;
import com.woowacourse.levellog.authentication.dto.LoginDto;
import com.woowacourse.levellog.authentication.support.PublicAPI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@PublicAPI
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody @Valid final GithubCodeDto request) {
        return ResponseEntity.ok(oAuthService.login(request));
    }
}
