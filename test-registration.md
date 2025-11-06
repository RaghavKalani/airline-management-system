# Registration Test Guide

## Password Requirements Fixed!
✅ Password now only needs to be **6+ characters** (simplified)

## Valid Registration Examples

### Example 1 - Basic User
```
Username: newuser
Password: password123
Confirm: password123
Full Name: New User
Email: newuser@email.com
Phone: 1234567890
Role: CUSTOMER
```

### Example 2 - Test User
```
Username: testuser
Password: test123
Confirm: test123
Full Name: Test User
Email: testuser@test.com
Phone: 9876543210
Role: CUSTOMER
```

### Example 3 - Customer
```
Username: customer1
Password: 123456
Confirm: 123456
Full Name: Customer One
Email: customer1@airline.com
Phone: 5551234567
Role: CUSTOMER
```

## Username Rules
✅ 3-20 characters
✅ Letters (a-z, A-Z)
✅ Numbers (0-9)
✅ Underscore (_)
❌ No spaces
❌ No special characters (except underscore)

## Email Rules
✅ Must contain @
✅ Must have domain (example@domain.com)
✅ Standard email format

## Phone Rules
✅ 10-15 digits only
✅ No spaces, dashes, or parentheses

## Password Rules (SIMPLIFIED)
✅ Minimum 6 characters
✅ Can be anything (letters, numbers, symbols)

## Existing Users (DON'T USE THESE)
❌ admin
❌ john_doe
❌ jane_smith

## Existing Emails (DON'T USE THESE)
❌ admin@airline.com
❌ john@email.com
❌ jane@email.com

---

## Troubleshooting

If registration still fails, check console output for:
- "Username already exists: [username]"
- "Email already exists: [email]"
- "Invalid username format: [username]"
- "Invalid email format: [email]"
- "Password is too weak"
