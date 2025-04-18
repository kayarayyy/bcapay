package com.bcaf.bcapay.models.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bcaf.bcapay.models.Branch;
import com.bcaf.bcapay.models.CustomerDetails;
import com.bcaf.bcapay.models.Feature;
import com.bcaf.bcapay.models.LoanRequest;
import com.bcaf.bcapay.models.Plafond;
import com.bcaf.bcapay.models.Role;
import com.bcaf.bcapay.models.RoleFeature;
import com.bcaf.bcapay.models.User;
import com.bcaf.bcapay.models.enums.City;
import com.bcaf.bcapay.models.enums.Plan;
import com.bcaf.bcapay.repositories.BranchRepository;
import com.bcaf.bcapay.repositories.CustomerDetailsRepository;
import com.bcaf.bcapay.repositories.FeatureRepository;
import com.bcaf.bcapay.repositories.LoanRequestRepository;
import com.bcaf.bcapay.repositories.PlafondRepository;
import com.bcaf.bcapay.repositories.RoleFeatureRepository;
import com.bcaf.bcapay.repositories.RoleRepository;
import com.bcaf.bcapay.repositories.UserRepository;
import com.bcaf.bcapay.services.BranchService;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class Seeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private RoleFeatureRepository roleFeatureRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    @Autowired
    private PlafondRepository plafondRepository;

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    private BranchService branchService;

    @Override
    @Transactional
    public void run(String... args) {
        seedRoles();
        seedFeatures();
        seedRoleFeatures();
        seedUsers();
        seedBranches();
        seedPlafond();
        seedCustomerDetails();
        seedLoanRequests();
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(null, "SUPERADMIN", null));
            roleRepository.save(new Role(null, "CUSTOMER", null));
            roleRepository.save(new Role(null, "MARKETING", null));
            roleRepository.save(new Role(null, "BRANCH_MANAGER", null));
            roleRepository.save(new Role(null, "BACK_OFFICE", null));
        }
    }

    private void seedFeatures() {
        if (featureRepository.count() == 0) {
            featureRepository.save(new Feature(null, "MANAGE_USERS", null));
            featureRepository.save(new Feature(null, "MANAGE_ROLES", null));
            featureRepository.save(new Feature(null, "MANAGE_ROLE_FEATURES", null));
            featureRepository.save(new Feature(null, "MANAGE_FEATURES", null));
            featureRepository.save(new Feature(null, "MANAGE_PROFILE", null));
            featureRepository.save(new Feature(null, "MANAGE_LOAN_REQUESTS", null));
            featureRepository.save(new Feature(null, "APPLY_LOAN", null));
            featureRepository.save(new Feature(null, "MARKETING_LOAN_ACTION", null));
            featureRepository.save(new Feature(null, "BRANCH_MANAGER_LOAN_ACTION", null));
            featureRepository.save(new Feature(null, "BACK_OFFICE_PROCEED", null));
            featureRepository.save(new Feature(null, "BACK_OFFICE_APPROVAL_DISBURSEMENT", null));
            featureRepository.save(new Feature(null, "ASSIGN_MARKETING", null));
        }
    }

    private void seedRoleFeatures() {
        if (roleFeatureRepository.count() == 0) {

            Role superAdmin = roleRepository.findByName("SUPERADMIN").orElse(null);
            Feature manageUsers = featureRepository.findByName("MANAGE_USERS").orElse(null);
            Feature manageRoles = featureRepository.findByName("MANAGE_ROLES").orElse(null);
            Feature manageFeatures = featureRepository.findByName("MANAGE_FEATURES").orElse(null);
            Feature manageRoleFeatures = featureRepository.findByName("MANAGE_ROLE_FEATURES").orElse(null);
            Feature manageLoanRequests = featureRepository.findByName("MANAGE_LOAN_REQUESTS").orElse(null);
            Feature applyLoan = featureRepository.findByName("APPLY_LOAN").orElse(null);

            Role customer = roleRepository.findByName("CUSTOMER").orElse(null);
            Feature manageProfile = featureRepository.findByName("MANAGE_PROFILE").orElse(null);

            Role marketing = roleRepository.findByName("MARKETING").orElse(null);
            Feature marketingLoanAction = featureRepository.findByName("MARKETING_LOAN_ACTION").orElse(null);

            Role branchManager = roleRepository.findByName("BRANCH_MANAGER").orElse(null);
            Feature branchManagerLoanAction = featureRepository.findByName("BRANCH_MANAGER_LOAN_ACTION").orElse(null);
            Feature branchManagerAssignMarketing = featureRepository.findByName("ASSIGN_MARKETING").orElse(null);

            Role backOffice = roleRepository.findByName("BACK_OFFICE").orElse(null);
            Feature backOfficeProceed = featureRepository.findByName("BACK_OFFICE_PROCEED").orElse(null);
            Feature backOfficeApprovalDisbursement = featureRepository.findByName("BACK_OFFICE_APPROVAL_DISBURSEMENT")
                    .orElse(null);

            if (superAdmin != null) {
                if (manageRoles != null) {
                    roleFeatureRepository.save(new RoleFeature(null, superAdmin, manageRoles));
                }
                if (manageUsers != null) {
                    roleFeatureRepository.save(new RoleFeature(null, superAdmin, manageUsers));
                }
                if (manageFeatures != null) {
                    roleFeatureRepository.save(new RoleFeature(null, superAdmin, manageFeatures));
                }
                if (manageRoleFeatures != null) {
                    roleFeatureRepository.save(new RoleFeature(null, superAdmin, manageRoleFeatures));
                }
                if (manageLoanRequests != null) {
                    roleFeatureRepository.save(new RoleFeature(null, superAdmin, manageLoanRequests));
                }
            }
            if (customer != null) {
                if (manageProfile != null) {
                    roleFeatureRepository.save(new RoleFeature(null, customer, manageProfile));
                }
                if (applyLoan != null) {
                    roleFeatureRepository.save(new RoleFeature(null, customer, applyLoan));
                }
            }

            if (marketing != null) {
                if (marketingLoanAction != null) {
                    roleFeatureRepository.save(new RoleFeature(null, marketing, marketingLoanAction));
                }
            }

            if (branchManager != null) {
                if (branchManagerLoanAction != null) {
                    roleFeatureRepository.save(new RoleFeature(null, branchManager, branchManagerLoanAction));
                }
                if (branchManagerAssignMarketing != null) {
                    roleFeatureRepository.save(new RoleFeature(null, branchManager, branchManagerAssignMarketing));
                }
            }

            if (backOffice != null) {
                if (backOfficeProceed != null) {
                    roleFeatureRepository.save(new RoleFeature(null, backOffice, backOfficeProceed));
                }
                if (backOfficeApprovalDisbursement != null) {
                    roleFeatureRepository.save(new RoleFeature(null, backOffice, backOfficeApprovalDisbursement));
                }
            }
        }
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            createUser("Superadmin", "superadmin@gmail.com", "superadmin123", "SUPERADMIN", null, null);
            createUser("Marketing", "marketing@gmail.com", "marketing123", "MARKETING", "2025111", "REF2025111");
            createUser("Marketing 1", "marketing1@gmail.com", "marketing123", "MARKETING", "2025112", "REF2025112");
            createUser("Customer", "customer@gmail.com", "customer123", "CUSTOMER", null, null);
            createUser("Branch Manager", "branchmanager@gmail.com", "branchmanager123", "BRANCH_MANAGER", "2025121",
                    null);
            createUser("Branch Manager 1", "branchmanager1@gmail.com", "branchmanager123", "BRANCH_MANAGER", "2025122",
                    null);
            createUser("Back Office", "backoffice@gmail.com", "backoffice123", "BACK_OFFICE", "2025131", null);
        }
    }

    private void createUser(String name, String email, String password, String roleName, String nip, String refferal) {
        User user = new User();
        user.setName(name);
        user.setActive(true);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(roleRepository.findByName(roleName).orElse(null));
        user.setNip(nip);
        user.setRefferal(refferal);
        userRepository.save(user);
    }

    private void seedBranches() {
        if (branchRepository.count() == 0) {
            List<Branch> branches = Arrays.asList(
                    new Branch(null, "Jakarta 1", City.JAKARTA, -6.2088, 106.8456, null, new ArrayList<>()),
                    new Branch(null, "Bandung 1", City.BANDUNG, -6.9175, 107.6191, null, new ArrayList<>()),
                    new Branch(null, "Surabaya 1", City.SURABAYA, -7.2575, 112.7521, null, new ArrayList<>()),
                    new Branch(null, "Medan 1", City.MEDAN, 3.5952, 98.6722, null, new ArrayList<>()),
                    new Branch(null, "Denpasar 1", City.DENPASAR, -8.6705, 115.2126, null, new ArrayList<>()));

            branchRepository.saveAll(branches);
        }

        // Tambahkan Branch Manager & Marketing untuk Jakarta
        Branch jakartaBranch = branchRepository.findByName("Jakarta 1").orElse(null);
        User managerJakarta = userRepository.findByEmail("branchmanager@gmail.com").orElse(null);
        User marketingJakarta = userRepository.findByEmail("marketing@gmail.com").orElse(null);

        if (jakartaBranch != null) {
            if (managerJakarta != null) {
                jakartaBranch.setBranchManager(managerJakarta);
            }

            if (marketingJakarta != null) {
                marketingJakarta.setBranch(jakartaBranch); // Set branch untuk user marketing
                jakartaBranch.getMarketing().add(marketingJakarta);
            }

            branchRepository.save(jakartaBranch); // Simpan perubahan
            if (marketingJakarta != null) {
                userRepository.save(marketingJakarta); // Simpan perubahan pada user
            }
        }

        Branch bandungBranch = branchRepository.findByName("Bandung 1").orElse(null);
        User managerBandung = userRepository.findByEmail("branchmanager1@gmail.com").orElse(null);
        User marketingBandung = userRepository.findByEmail("marketing1@gmail.com").orElse(null);

        if (bandungBranch != null) {
            if (managerBandung != null) {
                bandungBranch.setBranchManager(managerBandung);
            }

            if (marketingBandung != null) {
                marketingBandung.setBranch(bandungBranch); // Set branch untuk user marketing
                bandungBranch.getMarketing().add(marketingBandung);
            }

            branchRepository.save(bandungBranch); // Simpan perubahan
            if (marketingBandung != null) {
                userRepository.save(marketingBandung); // Simpan perubahan pada user
            }
        }
    }

    private void seedLoanRequests() {
        if (loanRequestRepository.count() == 0) {
            // Ambil customer dengan email customer@gmail.com
            Optional<CustomerDetails> customerDetails = customerDetailsRepository.findByUserEmail("customer@gmail.com");
            User customer = customerDetails.get().getUser();
            if (customer != null) {
                // Membuat LoanRequest dengan customer yang ditemukan
                LoanRequest loanRequest = new LoanRequest();
                loanRequest.setAmount(1000000.00); // 1 juta
                loanRequest.setCustomer(customer);
                loanRequest.setLatitude(-6.2870583);
                loanRequest.setLongitude(106.7820784);
                Branch branch = branchService.findNearestBranch(loanRequest.getLatitude(), loanRequest.getLongitude());
                loanRequest.setBranch(branch);
                loanRequest.setBranchManager(branch.getBranchManager());
                // Simpan LoanRequest ke dalam database
                loanRequestRepository.save(loanRequest);
            }
        }
    }

    private void seedPlafond() {
        if (plafondRepository.count() == 0) {
            plafondRepository.save(new Plafond(null, 1000000.00, Plan.BRONZE, 0.05));
            plafondRepository.save(new Plafond(null, 5000000.00, Plan.SILVER, 0.047));
            plafondRepository.save(new Plafond(null, 10000000.00, Plan.GOLD, 0.04));
            plafondRepository.save(new Plafond(null, 25000000.00, Plan.PLATINUM, 0.035));
        }
    }

    private void seedCustomerDetails() {
        if (userRepository.existsByEmail("customer@gmail.com") &&
                plafondRepository.existsByPlan(Plan.BRONZE)) {

            User customer = userRepository.findByEmail("customer@gmail.com").orElse(null);
            Plafond bronzePlafond = plafondRepository.findByPlan(Plan.BRONZE).orElse(null);

            if (customer != null && bronzePlafond != null) {
                // Cek apakah data customer detail sudah pernah dibuat
                boolean exists = customerDetailsRepository.findByUserEmail(customer.getEmail()).isPresent();
                if (!exists) {
                    CustomerDetails details = new CustomerDetails();
                    details.setUser(customer);
                    details.setPlafondPlan(bronzePlafond);
                    details.setAvailablePlafond(bronzePlafond.getAmount());

                    customerDetailsRepository.save(details);
                }
            }
        }
    }

}
