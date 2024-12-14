package com.zam.prueba.security.services;

import com.zam.prueba.entity.UserEntity;
import com.zam.prueba.repository.RoleRepository;
import com.zam.prueba.repository.UserRepository;
import com.zam.prueba.security.customs.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // Carga el usuario usando el OidcUserService base
        OidcUser oidcUser = super.loadUser(userRequest);

        // Accede a los datos del usuario desde el ID token
        Map<String, Object> userClaims = oidcUser.getIdToken().getClaims();

        // Extrae los datos del usuario, por ejemplo, el correo electrónico
        String email = (String) userClaims.get("email");
        String profilePicture = (String) userClaims.get("picture");

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    UserEntity newUser = UserEntity.builder()
                            .profilePicture(profilePicture)
                            .email(email)
                            .password("google")
                            .isEnabled(true)
                            .accountNoExpired(true)
                            .accountNoLocked(true)
                            .credentialNoExpired(true)
                            .roleList(Set.of(roleRepository.findById(2L).orElseThrow(() -> {
                                return new EntityNotFoundException("Role not found");
                            })))
                            .build();
                    return userRepository.save(newUser);
                });
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        return new DefaultOidcUser(customUserDetails.getAuthorities(), oidcUser.getIdToken(), oidcUser.getUserInfo());
    }

}
