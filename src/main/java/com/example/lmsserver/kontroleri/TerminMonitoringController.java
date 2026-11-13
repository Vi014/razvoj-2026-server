package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.response.monitoring.TerminMonitoringResponse;
import com.example.lmsserver.kontroleri.response.monitoring.TerminStatistikaResponse;
import com.example.lmsserver.servisi.TerminMonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/termin")
public class TerminMonitoringController
{
    private final TerminMonitoringService monitoringService;

    @PreAuthorize("hasRole('PREDAVAC')")
    @GetMapping("/{id}/monitoring")
    public TerminMonitoringResponse getMonitoring(@PathVariable Long id)
    {
        return monitoringService.getMonitoring(id);
    }

    @PreAuthorize("hasRole('PREDAVAC')")
    @GetMapping("/{id}/statistika")
    public TerminStatistikaResponse getStatistika(@PathVariable Long id)
    {
        return monitoringService.getStatistika(id);
    }
}
