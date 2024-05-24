import sys
import requests
from bs4 import BeautifulSoup
import json
import os

# 커맨드 라인에서 PMID 받기
if len(sys.argv) > 2:
    pmid = sys.argv[1]
    jourId = sys.argv[2]
else:
    print("PMID와 JourID를 커맨드 라인 인자로 제공해야 합니다.")
    sys.exit(1)

url = f"https://pubmed.ncbi.nlm.nih.gov/{pmid}/"
response = requests.get(url)
soup = BeautifulSoup(response.content, 'html.parser')

# 제목
title_section = soup.find('h1', {'class': 'heading-title'})
title = title_section.text.strip() if title_section else None

# 저자
authors = [author.get_text(strip=True) for author in soup.select('.authors-list .full-name')]

# 발간 날짜
date_section = soup.find('span', class_='cit')
date = date_section.text.split(';')[0].strip() if date_section else None

# PMCID
pmcid = soup.find('span', class_='identifier pmc').get_text(strip=True).replace('PMCID:', '').strip() if soup.find('span', class_='identifier pmc') else None

# DOI
doi = soup.find('span', class_='identifier doi').get_text(strip=True).replace('DOI:', '').strip() if soup.find('span', class_='identifier doi') else None

# 초록
abstract = None  # 이 줄을 추가하여 abstract 변수를 미리 None으로 초기화합니다.
abstract_section = soup.find('div', class_='abstract-content selected')
if abstract_section and abstract_section.p:
    em_tag = abstract_section.p.find('em')
    if not (em_tag and 'No abstract available' in em_tag.get_text()):
        abstract = abstract_section.p.text.strip()
        # "Objective:" 제거 로직을 여기에 넣어야 합니다.
        abstract = abstract.replace("Objective:", "").strip() if "Objective:" in abstract else abstract

# 키워드
keywords = None
abstract_section = soup.find('div', class_='abstract-content selected')
if abstract_section:
    # abstract-content selected 클래스가 닫힌 후 바로 나오는 p 태그를 찾음
    keywords_p_tag = abstract_section.find_next_sibling('p')
    if keywords_p_tag:
        # p 태그 내에서 strong 태그 찾기
        strong_tag = keywords_p_tag.find('strong')
        # strong 태그의 텍스트가 'Keywords:' 인지 확인
        if strong_tag and strong_tag.get_text(strip=True) == 'Keywords:':
            # strong 태그 다음의 텍스트 추출
            if strong_tag.next_sibling:
                keywords = strong_tag.next_sibling.strip()

# 유사한 논문 정보 추출
similar_titles = []
similar_pmids = []
similar_authors_list = []
similar_articles_section = soup.find('div', class_='similar-articles')
if similar_articles_section:
    similar_articles_list = similar_articles_section.find_all('li', class_='full-docsum')
    for article in similar_articles_list:
        similar_titles.append(article.find('a', class_='docsum-title').get_text(strip=True))
        similar_pmids.append(article.find('span', class_='docsum-pmid').get_text(strip=True))
        similar_authors_list.append(article.find('span', class_='docsum-authors').get_text(strip=True))

# 인용한 논문 정보 추출
cited_by_titles = []
cited_by_pmids = []
cited_by_authors_list = []
cited_by_section = soup.find('div', class_='citedby-articles')
if cited_by_section:
    cited_by_articles_list = cited_by_section.find_all('li', class_='full-docsum')
    for article in cited_by_articles_list:
        cited_by_titles.append(article.find('a', class_='docsum-title').get_text(strip=True))
        cited_by_pmids.append(article.find('span', class_='docsum-pmid').get_text(strip=True))
        cited_by_authors_list.append(article.find('span', class_='docsum-authors').get_text(strip=True))

# JSON 파일에 데이터 이어서 저장하기
# 파일저장 경로 상대경로로 바꾸기. - 완 -
base_dir = os.path.dirname(os.path.abspath(__file__))
json_file = os.path.join(base_dir, "json", "articles.json")

# 기존 파일에서 데이터 불러오기
existing_data = []  # 데이터 리스트 초기화
if os.path.exists(json_file):
    try:
        with open(json_file, 'r', encoding='utf-8-sig') as file:
            existing_data = json.load(file)
    except json.JSONDecodeError:
        existing_data = []

# 새로운 데이터를 기존 데이터에 추가
new_data = {
    "title": title,
    "authors": "/".join(authors),
    "date": date,
    "pmid": pmid,
    "pmcid": pmcid,
    "doi": doi,
    "abstract": abstract,
    "keywords": keywords,
    "s-titles": "/".join(similar_titles),
    "s-authors": "/".join(similar_authors_list),
    "s-pmids": "/".join(similar_pmids),
    "c-titles": "/".join(cited_by_titles),
    "c-authors": "/".join(cited_by_authors_list),
    "c-pmids": "/".join(cited_by_pmids),
    "jourId": jourId
}

existing_data.append(new_data)

# 업데이트된 전체 데이터를 파일에 저장
with open(json_file, 'w', encoding='utf-8-sig') as file:
    json.dump(existing_data, file, ensure_ascii=False, indent=4)

print(f"Crawling data with {pmid}")
print("dump data")