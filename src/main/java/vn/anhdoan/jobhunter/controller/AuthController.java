package vn.anhdoan.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.anhdoan.jobhunter.domain.dto.LoginDTO;
import vn.anhdoan.jobhunter.domain.dto.ResLoginDTO;
import vn.anhdoan.jobhunter.service.error.IdInvalidException;
import vn.anhdoan.jobhunter.util.SecurityUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // Phần dùng để xác thực người dùng, nhưng cần phải override lại hàm
        // 'loadUserByUsername'
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Create token
        String access_Token = this.securityUtil.createToken(authentication);

        // Set data user
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Custom phản hồi
        ResLoginDTO res = new ResLoginDTO();
        res.setAccess_token(access_Token);
        return ResponseEntity.ok().body(res);
    }

}
