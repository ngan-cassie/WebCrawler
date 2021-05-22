# WebCrawler Project
- This is built on top of my final project in Data Structures class, in which we built a spider and multi-threaded spider to crawl websites for links. 
- This project is to practice using hash table (HashMap, HashSet, etc.) data structure. 

## Calculate reading time of each article
- I use Jsoup library to extract article's content to calculate reading time (reading speed for an average adult is 225 wpm) and get the title.
- When run: user paste in the article's URL to start the multi-threaded spider

## Detect broken links
- If the link is broken, the program will output to inform the user which hyperlinked text has a broken link.
