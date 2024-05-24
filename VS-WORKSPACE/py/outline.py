from ast import Break
import requests
from bs4 import BeautifulSoup
import os
import json
import sys

def set_url(issn):
    # 초기 검색 URL 생성
    url = f"https://www.ncbi.nlm.nih.gov/nlmcatalog/?term={issn}"

    # 요청을 보내고 응답 받기
    response = requests.get(url)
    response.raise_for_status()

    # BeautifulSoup를 사용하여 HTML 파싱
    soup = BeautifulSoup(response.text, 'html.parser')

    # 굵은 글씨로 표시된 ISSN 항목 찾기
    bold_issn_tag = soup.find('b', string=issn)
    if bold_issn_tag:
        # 굵게 표시된 ISSN 항목에 해당하는 NLM ID 찾기
        dl_tag = bold_issn_tag.find_next('dl')
        if dl_tag:
            dt_tag = dl_tag.find('dt', string=lambda x: x and 'NLM ID:' in x)
            if dt_tag:
                dd_tag = dt_tag.find_next_sibling('dd')
                if dd_tag:
                    nlm_id = dd_tag.get_text(strip=True)
                    # NLM ID를 사용하여 새로운 URL 생성
                    url = f"https://www.ncbi.nlm.nih.gov/nlmcatalog/?term={nlm_id}"

    return url

def crawl_data_from_page(url, jour_id):
    res = requests.get(url)
    res.raise_for_status()

    soup = BeautifulSoup(res.text, 'html.parser')
    main = soup.find('dl', 'nlmcat_dl')

    print("1. Start Crawling")

    json_data = {"Jour ID": jour_id}  
    current_dt = None

    desired_keys = [
        "Author(s)", "NLM Title Abbreviation", "Title(s)", "Other Title(s)",
        "Publication Start Year", "Frequency", "Country of Publication",
        "Publisher", "Latest Publisher", "Language", "ISSN", "Coden",
        "LCCN", "Electronic Links", "MeSH", "Other ID", "NLM ID"
    ]

    for tag in main.find_all(['dt', 'dd']):
        if tag.name == 'dt':
            current_dt = tag.get_text(strip=True).replace(":", "")
            if current_dt in desired_keys:
                json_data[current_dt] = {}
        elif tag.name == 'dd' and current_dt and current_dt in desired_keys:
            dd_value = tag.get_text(strip=True).replace(":", "")
            dd_value = dd_value.replace("'", ",,")
            dd_value = dd_value.replace(";", ".,")
            json_data[current_dt] = dd_value

    for current_dt, dd_list in json_data.items():
        print(f"data {current_dt}: {dd_list}")

    print("2. return json data")
    return json_data

def save_to_json_file(jsonfile, data):
    if os.path.exists(jsonfile):
        with open(jsonfile, "r", encoding="utf-8-sig") as j:
            try:
                existing_data = json.load(j)
            except json.decoder.JSONDecodeError:
                existing_data = []
    else:
        existing_data = []

    existing_data.append(data)

    print("3. Add data")

    with open(jsonfile, "w", encoding="utf-8-sig") as j:
        json.dump(existing_data, j, ensure_ascii=False, indent=4)

    print("4. dump data")

def main():
    if len(sys.argv) != 3:
        print("cmd : python script.py <ISSN> <Jour ID>")
        sys.exit(1)

    issn = sys.argv[1]  
    jour_id = sys.argv[2]  
    jsonfile = "C:\\Dev\\VS-WORKSPACE\\json\\outline.json"

    seturl = set_url(issn)
    print(seturl)
    data_ = crawl_data_from_page(seturl,jour_id)

    if data_ is not None:
        save_to_json_file(jsonfile, data_)

if __name__ == "__main__":
    main()
