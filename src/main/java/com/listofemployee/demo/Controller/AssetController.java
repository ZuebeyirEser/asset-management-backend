package com.listofemployee.demo.Controller;

import com.listofemployee.demo.Exceptions.ResourceNotFoundExceptions;
import com.listofemployee.demo.Model.Asset;
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

/**
 * This class is a Spring REST controller that handles CRUD operations for assets.
 * It uses Lombok's `@RequiredArgsConstructor` annotation for constructor injection.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AssetController {
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;


    /**
     * Gets all assets for the authenticated user.
     *
     * @return a ResponseEntity containing a list of assets or an appropriate error message
     */
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

    /**
     * Creates a new asset for the authenticated user.
     *
     * @param asset the asset data to be created
     * @return the created asset
     */
    @PostMapping("/assets")
    public Asset createAsset(@RequestBody Asset asset) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User mot found: " + email));
        asset.setUser(user);
        return assetRepository.save(asset);
    }

    /**
     * Gets an asset by its ID for the authenticated user.
     *
     * @param id the ID of the asset to retrieve
     * @return a ResponseEntity containing the asset or an appropriate error message
     */
    @GetMapping("/assets/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found: " + email));

        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Asset not found with id: " + id));

        // Check if the asset belongs to the authenticated user
        if (!asset.getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(asset);
    }
}
