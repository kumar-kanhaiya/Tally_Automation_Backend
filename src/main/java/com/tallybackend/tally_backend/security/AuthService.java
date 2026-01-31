package com.tallybackend.tally_backend.security;

import com.tallybackend.tally_backend.dto.LoginRequestDto;
import com.tallybackend.tally_backend.dto.LoginResponseDto;
import com.tallybackend.tally_backend.dto.SignUpRequestDto;
import com.tallybackend.tally_backend.dto.SignUpResponseDto;
import com.tallybackend.tally_backend.entity.Type.ProviderType;
import com.tallybackend.tally_backend.entity.User;
import com.tallybackend.tally_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUtil authUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getUserId());
    }



    public User signUpInternal(SignUpRequestDto signUpRequestDto ,
                               ProviderType providerType , String providerId){
        User user = userRepository.findByUsername(signUpRequestDto.getUsername())
                .orElse(null);

        if(user != null ){
            throw new IllegalArgumentException("User already exist ");
        }

        user = User.builder()
                .username(signUpRequestDto.getUsername())
                .providerId(providerId)
                .providerName(providerType)
                .build();

        if(providerType == ProviderType.EMAIL){
            user.setProviderName(ProviderType.EMAIL);
            user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        }
//        System.out.println(user.getUserId() + " " + user.getUsername());
        return userRepository.save(user);

    }

    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto){
        User user = signUpInternal(signUpRequestDto,ProviderType.EMAIL , null);

        return new SignUpResponseDto(user.getUserId() , user.getUsername());
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User ,
                                                                     String registrationId){
        ProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User,registrationId);
        User user = userRepository.findByProviderIdAndProviderName(providerId , providerType).orElse(null);

        String email = oAuth2User.getAttribute("email");
//        String name = oAuth2User.getAttribute("name");

        User emailUser = userRepository.findByUsername(email).orElse(null);

        if(user == null && emailUser == null){
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User,registrationId,providerId);
            user = signUpInternal(new SignUpRequestDto(username , null)
                    ,providerType , providerId);
        }
        else if(user != null){
            if(email != null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }
        else{
            throw  new BadCredentialsException("This email is already registered with provider " +
                    emailUser.getProviderName());

        }
        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user),user.getUserId() );
        return ResponseEntity.ok(loginResponseDto);
    }

}
