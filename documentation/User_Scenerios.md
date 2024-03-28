# User Scenerios

## Features
 

## Scenerios


### User Management: Register & Login
Users should have functionality to register themselves or login to our website.
**Acceptance Criteria:**
* **Given** - I am a shelter and I am on FurreverHome's landing page
* **When -** I am a new user and I click on register option
* **Then -** I should be prompted to add shelter details & upload documents eg. Identification & contact information, documents like shelter license (shelter license is an important field to submit in this form).


* **Given-** I am a shelter and I am on FurreverHome's landing page
* **When-** I am registered and I click on login option
* **Then-** I put in the details and am able to successfully login


* **Given:** I am a shelter & my verification is pending
* **When:** I am trying to login
* **Then:** I should see a message indicating that verification is pending.


* **Given:** I am a shelter & my verification is rejected
* **When:** I am trying to login
* **Then:** I should see a message indicating that verification is rejected.


* **Given-** I am a pet lover/adopter and I am on FurreverHome's landing page
* **When-** I am a new user and I click on register option
* **Then-**  I should be prompted to add my personal details eg. ID & contact information, email, etc. After registering I should get a verification email on my email id


* **Given-** I am a pet lover/adopter and I am on FurreverHome's landing page
* **When-** I am registered and I click on login option
* **Then-** I put in the details and am able to successfully login


* **Given-** I am a pet lover/adopter and I am on FurreverHome's landing page
* **When-** I click on recover/reset my password
* **Then-** I should be able to reset my password through email verification


### User Profile Page
There should be a user profile section for user to check his profile details or even modify them.
**Acceptance Criteria :**
* **Given:** A User Profile Section link/button on the user home page.
* **When:** User/Shelter clicks on the user profile link/button.
* **Then:** A profile page with all my details is visible,
I can edit those details except the registered e-mail.
If I have adopted any animal, I should be able to see all my adoptions.

### Landing Page
User visits our website base url, and should be routed to landing page.
**Acceptance criteria:**
- As a user, I should get some information about our website and the services that we provide.
- As a user, I should have an option to register myself or login on to our website from the landing page.

### Home page (Shelter)
There should be home page for shelters after loggin in.
Acceptance Criteria :
* **Given:** I am a shelter & I have been successfully verified
* **When:** I am logged in
  * **Then:**
  - I will be routed to home page
  - I should be able to see all the pets currently registered in my shelter.
  - I should be able to update a pet status (adopted, in shelter), edit pet’s description, delete a pet, register new pets (The actual functionality is not expected in this story - these are the features available in from these pages, but should be implemented in other stories).
  - I should be able to click and open my profile and add/edit profile photo, details and descriptions about my profile except registered shelter id.

### Home page (Adopter)
There should be home page for adopters after logging in.
**Acceptance Criteria :**
* **Given:** I am a shelter & I have been successfully verified
* **When:** I am logged in
* **Then:**
    * I will be routed to home page
    * By default, I should be able to see I should be able to see shelters in the city that I am present (this info can be taken from user profile).
    * I should be able to click on any shelter to go to the shelter's individual page.
    * I should be able to click and open my profile.
    * I should be able to search for pets/shelters from home page


### Search Page
There will be two search bars: Search pets, Search shelters.
User can search based on locations, pet type, etc.
The tiles will change according the type of search (pet/shelter).
**Acceptance Criteria:**
* **Given**: An adopter is on home page
* **When:** Adopter doesn't add any search criteria on top search bar
* **Then:** Adopter should see shelters within 10 Km default range and/or updated results based on filters.


* **Given**: An adopter is on home page
* **When:** Adopter searches on animal type, location , breed, etc
* **Then:** Adopter should see latest results in the tiles based on the search.

### Page for Registration of pets
This feature is for shelters to register pets
**Acceptance Criteria:**
* **Given:** I am logged in as a shelter
* **When:** I click on register a new pet
* **Then:** 
    - I should be able to add all details corresponding to that pet . Details like - What Animal , breed ,
  color, weight, age, vaccination , description ,images, etc.
    - I should be able to modify the details if needed or even delete a
    registered pet.

### Individual Shelter Page
**Acceptance Criteria :**
* **Given:** User is on homepage and sees the default searched shelters.
* **When:** User clicks on a specific shelter.
* **Then:**
  - Adopters should be routed to the individual shelter page where they can see shelter details & all the pets registered in that particular shelter.
  - Adopter can click on any pet to route to that specific pet's page

### Individual Pet Page
- There should be a page for viewing individual pet.
- This can be opened through shelter home page (for shelters) or individual shelter page (for adopters).
* **Given:** I am a shelter
* **When:** I click on a pet tile on shelter home page
* **Then:** I should be routed to individual pet page


* **Given:** I am an adopter
* **When:** I click on a pet tile on a shelter's page
* **Then:** I should be routed to individual pet page


* **Given:** I am an adopter
* **When:** I click on a pet tile on a search page results (pet tiles)
* **Then:** I should be routed to individual pet page

**Acceptance Criteria :**
  - Adopters should be routed to the individual shelter page where they can see shelter details & all the pets registered in that particular shelter.
  - Adopter can click on any pet to route to that specific pet's page
  - As an adopter & shelter, I should be able to click on a pet image and route to individual pet page where all the information related to pet is mentioned.
  - As a shelter, I should have extra features like modifying or deleting this pet listing
  - I can add vaccination details for the pet and medical background.
  - I can add specific instructions on caring for this pet.
  - As an adopter, I should be able to see the shelter details on the right side.
  - As an adopter, I should be able to send queries to the shelter for adopting pet.
  - As an adopter, when I adopt a pet from a shelter, this pet should be added to my profile.
  [Note: How the adopter should contact the shelter is not clear, it would be great if the development team could come up with a solution.]

    
### Chat Feature
User should be able to contact Shelter via chat

**Acceptance Criteria :**
* **Given:** I am a User
* **When:** I click on a shelter’s page
* **Then:** I should see a button to chat with shelter I should be able to send a message to the shelter. Shelter can see you msg and respond I can send images in chat as well

### Admin Panel
There should be an admin role who can accepts or reject the shelter register request.
**Acceptance Criteria :**
* **Given:** Admin visits the login page.
* **When:** Clicks on login from landing page.
* **Then:**
  - Admin can see all the pending shelter registration requests.
  - Admin can see view new registered shelter details to verify.

* **Given:** Approves the shelter registration request.
* **When:** Admin click on approve button.
* **Then:** Shelter registration request would be verified.
* **Given:** Rejects the shelter registration request.
* **When:** Admin clicks on reject button.
* **Then:** Shelter registration request would be rejected.

### Pet vaccination reminders
User should get vaccination reminders on emails
**Acceptance Criteria :**
* **Given:** I am a User 
* **When:** I have adopted a pet from a shelter
* **Then:** I will receive timely reminders on my email that my pet’s next vaccination is due on this day
This reminder will be received a week before the vaccination due date

### Lost & Found Page
There should be a lost and found section for user to search for lost and found pet.
**Acceptance Criteria :**

* **Given:** A Lost & Found Section link/button on the adopter home page.
* **When:** Adopter clicks on the lost and found link/button.
* **Then:** A lost and found page, where there are options to search for a lost pet, and register a pet that was found.
* **Given:** A search section on the Lost & Found section.
* **When:** Adopter searches based on the characteristics.
* **Then:** All the similar pets results will be shown to the User.
* **Given:** A register lost pet section on the Lost & Found section.
* **When:** Adopter registers a pet.
* **Then:**
  - Adopter should be able add the pet's features and their own personal details (phone number, email). 
  - Adopter should be able to edit the pet's features and their personal details.


[**Go back to README.md**](../README.md)