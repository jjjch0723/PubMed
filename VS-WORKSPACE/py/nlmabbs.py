import sys
import requests
from bs4 import BeautifulSoup
import json
import os

'''추가할거 상위 100개 csv파일로 나타내기. '''
'''sql문은 작성 완료'''

def get_total_pages(soup):
    print("Finding total pages...")
    total_pages_tag = soup.find('label', class_='of-total-pages')
    if total_pages_tag:
        total_pages_text = total_pages_tag.get_text(strip=True)
        total_pages = int(total_pages_text.split(' ')[-1].replace(',', ''))
    else:
        total_pages = 1
    print(f"Total pages: {total_pages}")
    return total_pages

def get_titles_and_pmids(url, abbr, jour_id):
    print(f"Fetching data from {url}...")
    response = requests.get(url)
    if response.status_code != 200:
        print(f"Failed to fetch data from {url}")
        return []

    soup = BeautifulSoup(response.content, 'html.parser')

    # 'search-results-list' 클래스의 section을 찾음
    search_results_section = soup.find('section', class_='search-results-list')
    articles = search_results_section.find_all('div', class_='docsum-content') if search_results_section else []

    titles_and_pmids = []
    for article in articles:
        title_tag = article.find('a', class_='docsum-title')
        title = title_tag.get_text(strip=True) if title_tag else ''

        pmid_tag = article.find('span', class_='docsum-pmid')
        pmid = pmid_tag.get_text(strip=True) if pmid_tag else ''

        titles_and_pmids.append({"jour_id": jour_id, "abbr": abbr, "title": title, "pmid": pmid})

    print(f"Found {len(titles_and_pmids)} articles on this page.")
    return titles_and_pmids

if len(sys.argv) > 2:
    search_term_url = sys.argv[1]  # URL에 사용할 검색어
    jour_id = sys.argv[2]  # jour_id 
    abbr = search_term_url.replace('+', ' ')  # 'abbr'로 이름 변경
else:
    print("검색어와 jour_id를 커맨드 라인 인자로 제공해야 합니다.")
    sys.exit(1)

print(f"abbreviation :{abbr}")
base_url = f"https://pubmed.ncbi.nlm.nih.gov/?term={search_term_url}"

print(f"Fetching initial page : {base_url}")
response = requests.get(base_url)
soup = BeautifulSoup(response.content, 'html.parser')
total_pages = get_total_pages(soup)

all_data = []

# 문제야 문제
for page in range(1, total_pages + 1):
    if page > 10:
        break  # 페이지 번호가 10을 넘으면 루프 중단, 원래는 1000(10개*1000페이지 = 1만개)
    page_url = f"{base_url}&page={page}"
    page_data = get_titles_and_pmids(page_url, abbr, jour_id)
    all_data.extend(page_data)
# 문제야 문제

base_dir = os.path.dirname(os.path.abspath(__file__))
json_file = os.path.join(base_dir, "json", "pubmed_articles.json")

# 기존 파일에서 데이터 불러오기
if os.path.exists(json_file):
    with open(json_file, 'r') as file:
        try:
            existing_data = json.load(file)
        except json.JSONDecodeError:
            existing_data = []
else:
    existing_data = []

# 기존 데이터에 새로운 데이터 추가
all_data = existing_data + all_data

# 업데이트된 전체 데이터를 파일에 저장
with open(json_file, 'w') as file:
    json.dump(all_data, file, indent=4)

print(f"Data saved in file: {json_file}")
