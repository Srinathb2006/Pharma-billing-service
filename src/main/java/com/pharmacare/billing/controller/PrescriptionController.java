package com.pharmacare.billing.controller;

import com.pharmacare.billing.entity.Prescription;
import com.pharmacare.billing.repository.PrescriptionRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final PrescriptionRepository repository;

    public PrescriptionController(PrescriptionRepository repository) {
        this.repository = repository;
    }

   
    @PostMapping("/upload")
    public Prescription upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("patientName") String patientName,
            @RequestParam("patientId") String patientId,
            @RequestParam("doctorName") String doctorName,
            @RequestParam("medicines") List<String> medicines) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new RuntimeException("Only image files allowed");
        }

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Prescription prescription = new Prescription();
        prescription.setUserId(userId);
        prescription.setPatientName(patientName);
        prescription.setPatientId(patientId);
        prescription.setDoctorName(doctorName);
        prescription.setMedicines(medicines);
        prescription.setStatus("PENDING");

        Prescription saved = repository.save(prescription);

     
        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.contains(".")) {
            throw new RuntimeException("Invalid file name");
        }

        String extension = originalName.substring(originalName.lastIndexOf("."));
        String fileName = "prescription_" + saved.getId() + extension;

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        saved.setImagePath(filePath.toString());

        return repository.save(saved);
    }
    @PutMapping("/{id}/status")
    public Prescription updateStatus(
            @PathVariable String id,
            @RequestParam String status) {

        Prescription prescription = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        if (!status.equalsIgnoreCase("VERIFIED") &&
            !status.equalsIgnoreCase("REJECTED") &&
            !status.equalsIgnoreCase("PENDING")) {
            throw new RuntimeException("Invalid status");
        }

        prescription.setStatus(status.toUpperCase());

        return repository.save(prescription);
    }

  
    @GetMapping
    public List<Prescription> getAll() {
        return repository.findAll();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> view(@PathVariable String id) throws IOException {
        Prescription prescription = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        if (prescription.getImagePath() == null) {
            throw new RuntimeException("Image path not found");
        }

        Path path = Paths.get(prescription.getImagePath());

        if (!Files.exists(path)) {
            throw new RuntimeException("Image file not found on server");
        }

        Resource resource = new UrlResource(path.toUri());

       
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) throws IOException {

        Prescription prescription = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        if (prescription.getImagePath() != null) {
            Files.deleteIfExists(Paths.get(prescription.getImagePath()));
        }

        repository.deleteById(id);

        return ResponseEntity.ok("Prescription deleted successfully");
    }
}
