# 🚀 GitHub Upload Guide

## Step-by-Step Instructions to Upload Your Project to GitHub

### Option 1: Using GitHub Desktop (Easiest)

1. **Download GitHub Desktop**
   - Go to https://desktop.github.com/
   - Download and install

2. **Sign in to GitHub**
   - Open GitHub Desktop
   - File → Options → Accounts → Sign in

3. **Create Repository**
   - File → New Repository
   - Name: `flight_management` or `airline-management-system`
   - Local Path: `C:\Users\ragha\Desktop\flight_management`
   - Check "Initialize with README" → UNCHECK (we already have one)
   - Click "Create Repository"

4. **Publish to GitHub**
   - Click "Publish repository" button
   - Choose public or private
   - Click "Publish repository"

5. **Done!** Your repository is now on GitHub.

---

### Option 2: Using Git Command Line

#### Step 1: Install Git
- Download from: https://git-scm.com/download/win
- Install with default settings

#### Step 2: Create GitHub Repository
1. Go to https://github.com
2. Click "+" → "New repository"
3. Repository name: `flight_management`
4. Description: "Java Swing Airline Management System with MySQL"
5. Choose Public or Private
6. **DO NOT** check "Initialize with README" (we already have one)
7. Click "Create repository"

#### Step 3: Initialize Local Repository

Open PowerShell in your project folder and run:

```powershell
# Navigate to your project
cd C:\Users\ragha\Desktop\flight_management

# Initialize git repository
git init

# Configure git (replace with your info)
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"

# Add all files to staging
git add .

# Commit the files
git commit -m "Initial commit: Complete Airline Management System with animated UI"

# Add remote repository (replace YOUR_USERNAME with your GitHub username)
git remote add origin https://github.com/YOUR_USERNAME/flight_management.git

# Push to GitHub
git branch -M main
git push -u origin main
```

#### Step 4: Enter GitHub Credentials
- When prompted, enter your GitHub username and password
- Or use Personal Access Token (recommended)

---

### Option 3: Using VS Code (You're Already Using It!)

#### Step 1: Initialize Repository

1. **Open Source Control Panel**
   - Click the Source Control icon in the left sidebar (looks like a branch)
   - Or press `Ctrl + Shift + G`

2. **Initialize Repository**
   - Click "Initialize Repository" button
   - Confirm the folder location

3. **Stage All Files**
   - You'll see all your files listed
   - Click the "+" icon next to "Changes" to stage all files
   - Or click "+" next to individual files

4. **Commit Changes**
   - Type a commit message in the text box at the top:
     ```
     Initial commit: Complete Airline Management System
     
     - Animated Swing UI with dark theme
     - BCrypt authentication
     - Complete CRUD operations
     - Customer and Admin dashboards
     - Flight booking system
     ```
   - Click the checkmark (✓) or press `Ctrl + Enter`

#### Step 2: Create GitHub Repository

1. **Go to GitHub.com**
   - Sign in to your account
   - Click "+" → "New repository"
   - Name: `flight_management`
   - Make it Public or Private
   - **DO NOT** initialize with README
   - Click "Create repository"

2. **Copy the Repository URL**
   - Copy the HTTPS URL: `https://github.com/YOUR_USERNAME/flight_management.git`

#### Step 3: Push to GitHub from VS Code

1. **Add Remote Repository**
   - Open Terminal in VS Code: `Ctrl + ` (backtick)
   - Run:
     ```powershell
     git remote add origin https://github.com/YOUR_USERNAME/flight_management.git
     git branch -M main
     ```

2. **Push to GitHub**
   - In Source Control panel, click the three dots (⋯) menu
   - Select "Push"
   - Or run in terminal:
     ```powershell
     git push -u origin main
     ```

3. **Sign in to GitHub**
   - VS Code will prompt you to sign in
   - Follow the authentication flow

---

## ⚠️ Before You Push - Checklist

Make sure these files exist:
- ✅ `.gitignore` - Excludes unnecessary files
- ✅ `README.md` - Project documentation
- ✅ `LICENSE` - MIT License
- ✅ `database.sql` - Database schema
- ✅ `pom.xml` - Maven configuration
- ✅ `DEPLOYMENT_GUIDE.md` - Deployment instructions

**DO NOT** commit these (already in .gitignore):
- ❌ `target/` folder (compiled files)
- ❌ `.vscode/` folder (VS Code settings)
- ❌ `.class` files
- ❌ `.jar` files (except distribution JAR if needed)

---

## 🔐 Using Personal Access Token (Recommended)

GitHub no longer accepts passwords. Use a Personal Access Token:

1. **Generate Token**
   - Go to: https://github.com/settings/tokens
   - Click "Generate new token (classic)"
   - Note: "Git operations"
   - Expiration: Choose duration
   - Select scopes: Check "repo"
   - Click "Generate token"
   - **COPY THE TOKEN** (you won't see it again!)

2. **Use Token as Password**
   - When git asks for password, paste the token instead

---

## 📝 Common Git Commands

```powershell
# Check status
git status

# Add specific file
git add filename.java

# Add all files
git add .

# Commit with message
git commit -m "Your message here"

# Push to GitHub
git push

# Pull latest changes
git pull

# View commit history
git log

# Create new branch
git checkout -b feature-branch-name

# Switch branch
git checkout main
```

---

## 🎯 Quick Reference

### After Making Changes:

```powershell
# 1. Stage changes
git add .

# 2. Commit changes
git commit -m "Description of changes"

# 3. Push to GitHub
git push
```

### Clone Your Repository Later:

```powershell
git clone https://github.com/YOUR_USERNAME/flight_management.git
```

---

## 🆘 Troubleshooting

### Error: "remote origin already exists"
```powershell
git remote remove origin
git remote add origin https://github.com/YOUR_USERNAME/flight_management.git
```

### Error: "Permission denied"
- Use Personal Access Token instead of password
- Or set up SSH keys

### Error: "Updates were rejected"
```powershell
git pull origin main --allow-unrelated-histories
git push origin main
```

### Large File Warning
- Make sure `.gitignore` is working
- Remove large files: `git rm --cached target/* -r`

---

## ✨ What Happens After Upload?

Once uploaded, your repository will be available at:
```
https://github.com/YOUR_USERNAME/flight_management
```

You can:
- ✅ Share the link with anyone
- ✅ Others can clone and run your project
- ✅ Add it to your resume/portfolio
- ✅ Collaborate with team members
- ✅ Track changes over time

---

## 🎓 Tips for Academic Projects

1. **Make it Public** - Showcases your work to employers
2. **Write Good Commit Messages** - Shows professionalism
3. **Add Screenshots** - Create `screenshots/` folder with UI images
4. **Update README** - Keep documentation current
5. **Star Your Own Repo** - Shows engagement (optional)

---

## 📞 Need Help?

If you encounter issues:
1. Check the error message carefully
2. Search on Stack Overflow
3. Ask on GitHub Community Discussions
4. Contact your team members

---

**Good luck with your GitHub upload! 🚀**
