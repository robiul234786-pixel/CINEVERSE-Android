# CINEVERSE — Native Android Starter

এটি CINEVERSE-এর প্রথম Native Android (Kotlin + Jetpack Compose) UI starter project।

## এখন কী আছে
- Dark cinematic home screen
- Bottom navigation: Home, Explore, My List, Profile
- Empty states for catalog and Continue Watching
- GitHub Actions workflow to build a debug APK

## এখনো কী নেই
- Firebase login/database integration
- Admin panel
- Real movie catalog
- YouTube embedded player
- Own-video hosting/streaming

এসব পরের ধাপে যুক্ত হবে। Firebase config বা API key এই starter-এ রাখা হয়নি।

## GitHub দিয়ে বিনামূল্যে APK build
1. ZIP extract করে সব ফাইল একটি নতুন GitHub repository-তে upload করো।
2. Repository-তে `Actions` tab খোলো।
3. `Build CINEVERSE APK` workflow নির্বাচন করে `Run workflow` চাপো।
4. Build সফল হলে Artifacts থেকে `CINEVERSE-debug-apk` ডাউনলোড করো।

## Package
`com.cineverse.app`

## Build note
এই starter code এখানে Android build করে পরীক্ষা করা হয়নি। GitHub Actions-এ build চালিয়ে error হলে log অনুযায়ী ঠিক করতে হবে।
\n\nCINEVERSE BRANDING UPDATE\n- Premium black and gold launcher icon added for Android density buckets.\n- Android splash screen branding added.\n- App display name: CINEVERSE.\n- Splash screen and launcher icon use app branding image.\n