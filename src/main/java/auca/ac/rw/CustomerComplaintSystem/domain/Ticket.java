package auca.ac.rw.CustomerComplaintSystem.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private ETicketStatus status;

    // Many tickets created by one user
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    // Many-to-Many: a ticket can be assigned to many staff members
    // @JoinTable creates the "ticket_staff" join table automatically
    @ManyToMany
    @JoinTable(
        name = "ticket_staff",
        joinColumns = @JoinColumn(name = "ticket_id"),
        inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private List<SupportStaff> assignedStaff = new ArrayList<>();

    // --- Getters and Setters ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ETicketStatus getStatus() { return status; }
    public void setStatus(ETicketStatus status) { this.status = status; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public List<SupportStaff> getAssignedStaff() { return assignedStaff; }
    public void setAssignedStaff(List<SupportStaff> assignedStaff) { this.assignedStaff = assignedStaff; }
}