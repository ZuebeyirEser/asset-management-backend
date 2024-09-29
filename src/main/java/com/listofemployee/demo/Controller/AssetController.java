package com.listofemployee.demo.Controller;

import com.listofemployee.demo.Exceptions.ResourceNotFoundExceptions;
import com.listofemployee.demo.Model.Asset;
import com.listofemployee.demo.Model.Employee;
import com.listofemployee.demo.Model.User;
import com.listofemployee.demo.Repository.AssetRepository;
import com.listofemployee.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController("/api/v2/")
// this is alternative way to inject
/*
* Basically instead of construction injection we are using lombok annotation
* */
@RequiredArgsConstructor
public class AssetController {
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    @GetMapping("/assets")
    public ResponseEntity<?> getAllAssets() {
        //authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        // Since user might be null we prefer to use optional
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User mot found: " + email)));
        List<Asset> assets = assetRepository.findByUser(user);
        return ResponseEntity.ok(assets);
    }

    @PostMapping("/assets")
    public Asset createAsset(@RequestBody Asset asset) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User mot found: " + email));
        asset.setUser(user);
        return assetRepository.save(asset);
    }
}
